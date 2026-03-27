package me.drex.world_gamerules.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.drex.message.api.LocalizedMessage;
import me.drex.world_gamerules.command.selector.*;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gamerules.GameRule;

import java.util.Collection;

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

        for (GameRule<?> rule : BuiltInRegistries.GAME_RULE) {
            registerGameRule(gamerule, rule, dimensionSelectors);
        }
        dispatcher.register(gamerule);
    }

    private static <T> void registerGameRule(LiteralArgumentBuilder<CommandSourceStack> gamerule, GameRule<T> rule, DimensionSelector[] dimensionSelectors) {
        for (DimensionSelector dimensionSelector : dimensionSelectors) {
            DimensionSelector.Builders getRule = dimensionSelector.builder();
            DimensionSelector.Builders setRule = dimensionSelector.builder();

            // gamerule <gamerule> <dimension_selector>
            getRule.second().executes(ctx -> getRule(ctx, dimensionSelector.getLevels(ctx), rule));

            // gamerule <gamerule> <value> <dimension_selector>
            setRule.second().executes(ctx -> setRule(ctx, dimensionSelector.getLevels(ctx), rule));


            // Support for dimension selectors with two arguments
            getRule.connect();
            setRule.connect();

            gamerule.then(
                literal(rule.id())
                    .executes(ctx -> getRule(ctx, Lists.newArrayList(ctx.getSource().getServer().getAllLevels()), rule))
                    .then(
                        Commands.argument("value", rule.argument())
                            .executes(ctx -> setRule(ctx, Lists.newArrayList(ctx.getSource().getServer().getAllLevels()), rule))
                            .then(setRule.first())
                    )
                    .then(getRule.first())
            );
        }
    }

    static <T> int getRule(CommandContext<CommandSourceStack> commandContext, Collection<ServerLevel> levels, GameRule<T> rule) {
        CommandSourceStack src = commandContext.getSource();
        if (levels.isEmpty()) {
            src.sendFailure(LocalizedMessage.localized("world-gamerules.commands.gamerule.set.empty"));
            return 0;
        }

        MutableComponent list = ComponentUtils.formatList(levels, LocalizedMessage.localized("world-gamerules.commands.gamerule.get.list.separator"), level -> {
            T value = level.getGameRules().get(rule);
            return LocalizedMessage.builder("world-gamerules.commands.gamerule.get.list.element")
                .addPlaceholder("value", rule.serialize(value))
                .addPlaceholder("dimension", level.dimension().identifier().toString())
                .build();
        });

        src.sendSuccess(() -> LocalizedMessage.builder("world-gamerules.commands.gamerule.get")
            .addPlaceholder("gamerule", rule.id())
            .addPlaceholder("list", list)
            .build(), true);

        T value = commandContext.getSource().getLevel().getGameRules().get(rule);
        return rule.getCommandResult(value);
    }

    static <T> int setRule(CommandContext<CommandSourceStack> commandContext, Collection<ServerLevel> levels, GameRule<T> rule) {
        CommandSourceStack src = commandContext.getSource();
        if (levels.isEmpty()) {
            src.sendFailure(LocalizedMessage.localized("world-gamerules.commands.gamerule.set.empty"));
            return 0;
        }

        T parsedValue = commandContext.getArgument("value", rule.valueClass());
        String stringValue = rule.serialize(parsedValue);

        for (ServerLevel level : levels) {
            level.getGameRules().set(rule, parsedValue, commandContext.getSource().getServer());
        }

        src.sendSuccess(() -> LocalizedMessage.builder("world-gamerules.commands.gamerule.set")
            .addPlaceholder("gamerule", rule.id())
            .addPlaceholder("value", stringValue)
            .addPlaceholder("dimensions", levels.size())
            .build(), true);

        T value = commandContext.getSource().getLevel().getGameRules().get(rule);
        return rule.getCommandResult(value);
    }
}