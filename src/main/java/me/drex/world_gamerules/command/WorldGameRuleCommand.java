package me.drex.world_gamerules.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.drex.message.api.LocalizedMessage;
import me.drex.world_gamerules.command.selector.*;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.GameRules;

import java.util.Collection;
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
            DimensionSelector.Builders builders = dimensionSelector.builder();
            new GameRules(FeatureFlags.REGISTRY.allFlags()).visitGameRuleTypes(new GameRules.GameRuleTypeVisitor() {
                @Override
                public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
                    builders.second().then(
                        literal(key.getId()).then(
                            type.createArgument("value").executes(ctx -> setRule(ctx, dimensionSelector.getLevels(ctx), key))
                        ).executes(ctx -> getRule(ctx, dimensionSelector.getLevels(ctx), key))
                    );
                }
            });
            // Support for dimension selectors with two arguments
            if (builders.first() != builders.second()) {
                builders.first().then(builders.second());
            }
            gamerule.then(builders.first());
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
            return LocalizedMessage.builder("world-gamerules.commands.gamerule.get.list.element")
                .addPlaceholder("value", value.toString())
                .addPlaceholder("dimension", level.dimension().location().toString())
                .build();
        });

        src.sendSuccess(() -> LocalizedMessage.builder("world-gamerules.commands.gamerule.get")
            .addPlaceholder("gamerule", key.getId())
            .addPlaceholder("list", list)
            .build(), true);
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
        src.sendSuccess(() -> LocalizedMessage.builder("world-gamerules.commands.gamerule.set")
            .addPlaceholder("gamerule", key.getId())
            .addPlaceholder("value", finalStringValue)
            .addPlaceholder("dimensions", levels.size())
            .build(), true);
        return commandResult;
    }

}
