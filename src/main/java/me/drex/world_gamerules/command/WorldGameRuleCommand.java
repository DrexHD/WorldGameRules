package me.drex.world_gamerules.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.drex.message.api.LocalizedMessage;
import me.drex.world_gamerules.command.selector.*;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.commands.Commands.literal;

public class WorldGameRuleCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        DimensionSelector[] dimensionSelectors = new DimensionSelector[]{
            new AllDimensions(),
            new NamespaceDimensions(),
            new RegexDimensions(),
            new SingleDimension(),
        };

        LiteralArgumentBuilder<CommandSourceStack> gamerule = literal("gamerule").requires(Permissions.require("world-gamerules.commands.gamerule", 2));
        for (DimensionSelector dimensionSelector : dimensionSelectors) {
            DimensionSelector.Builders getRule = dimensionSelector.builder();
            DimensionSelector.Builders setRule = dimensionSelector.builder();
            GameRules.visitGameRuleTypes(new GameRules.GameRuleTypeVisitor() {
                @Override
                public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
                    // gamerule <gamerule> <dimension_selector>
                    getRule.second().executes(ctx -> getRule(ctx, dimensionSelector.getLevels(ctx), key));
                    // gamerule <gamerule> <value> <dimension_selector>
                    setRule.second().executes(ctx -> setRule(ctx, dimensionSelector.getLevels(ctx), key));

                    // Support for dimension selectors with two arguments
                    getRule.connect();
                    setRule.connect();

                    gamerule.then(
                        literal(key.getId())
                            // gamerule <gamerule>
                            .executes(ctx -> getRule(ctx, Lists.newArrayList(ctx.getSource().getServer().getAllLevels()), key))
                            .then(
                                type.createArgument("value")
                                    // gamerule <gamerule> <value>
                                    .executes(ctx -> setRule(ctx, Lists.newArrayList(ctx.getSource().getServer().getAllLevels()), key))
                                    .then(
                                        setRule.first()
                                    )
                            ).then(
                                getRule.first()
                            )
                    );
                }
            });
        }

        dispatcher.register(gamerule);
    }

    static <T extends GameRules.Value<T>> int getRule(CommandContext<CommandSourceStack> commandContext, Collection<ServerLevel> levels, GameRules.Key<T> key) {
        CommandSourceStack src = commandContext.getSource();
        if (levels.isEmpty()) {
            src.sendFailure(LocalizedMessage.localized("world-gamerules.commands.gamerule.set.empty"));
            return 0;
        }

        AtomicInteger commandResult = new AtomicInteger();

        MutableComponent list = ComponentUtils.formatList(levels, LocalizedMessage.localized("world-gamerules.commands.gamerule.get.list.separator"), level -> {
            T value = level.getGameRules().getRule(key);
            commandResult.addAndGet(value.getCommandResult());
            return LocalizedMessage.localized("world-gamerules.commands.gamerule.get.list.element", Map.of(
                    "value", Component.literal(value.toString()),
                    "dimension", Component.literal(level.dimension().location().toString())
                ));
        });

        src.sendSuccess(() -> LocalizedMessage.localized("world-gamerules.commands.gamerule.get", Map.of(
                "gamerule", Component.literal(key.getId()),
                "list", list
            )), true);
        return commandResult.get();
    }

    static <T extends GameRules.Value<T>> int setRule(CommandContext<CommandSourceStack> commandContext, Collection<ServerLevel> levels, GameRules.Key<T> key) {
        CommandSourceStack src = commandContext.getSource();
        if (levels.isEmpty()) {
            src.sendFailure(LocalizedMessage.localized("world-gamerules.commands.gamerule.set.empty"));
            return 0;
        }

        String stringValue = "";
        int commandResult = 0;
        for (ServerLevel level : levels) {
            T value = level.getGameRules().getRule(key);
            value.setFromArgument(commandContext, "value");
            stringValue = value.toString();
        }

        String finalStringValue = stringValue;
        src.sendSuccess(() -> LocalizedMessage.localized("world-gamerules.commands.gamerule.set", Map.of(
                "gamerule", Component.literal(key.getId()),
                "value", Component.literal(finalStringValue),
                "dimensions", Component.literal(String.valueOf(levels.size()))
            )), true);
        return commandResult;
    }

}
