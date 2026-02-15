package me.drex.world_gamerules.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.drex.message.api.LocalizedMessage;
import me.drex.world_gamerules.command.selector.*;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
//? if >= 1.21.11 {
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gamerules.GameRule;
//?} else {
/*import net.minecraft.world.level.GameRules;
        *///?}

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.commands.Commands.literal;

public class WorldGameRuleCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher /*? if < 1.21.11 {*//*, CommandBuildContext context*//*?}*/) {
        DimensionSelector[] dimensionSelectors = new DimensionSelector[]{
                new AllDimensions(),
                new NamespaceDimensions(),
                new RegexDimensions(),
                new SingleDimension(),
        };

        LiteralArgumentBuilder<CommandSourceStack> gamerule = literal("gamerule").requires(Permissions.require("world-gamerules.commands.gamerule", 2));

        //? if >= 1.21.11 {
        for (GameRule<?> rule : BuiltInRegistries.GAME_RULE) {
            registerGameRule(gamerule, rule, dimensionSelectors);
        }
        //?} else {

        /*new GameRules(/^? if >= 1.21.4 {^/context.enabledFeatures()/^?}^/).visitGameRuleTypes(new GameRules.GameRuleTypeVisitor() {
            @Override
            public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
                registerGameRule(gamerule, key, type, dimensionSelectors);
            }
        });
        *///?}

        dispatcher.register(gamerule);
    }

    //? if >= 1.21.11 {
    private static <T> void registerGameRule(LiteralArgumentBuilder<CommandSourceStack> gamerule, GameRule<T> rule, DimensionSelector[] dimensionSelectors) {
     //?} else {
    /*private static <T extends GameRules.Value<T>> void registerGameRule(LiteralArgumentBuilder<CommandSourceStack> gamerule, GameRules.Key<T> key, GameRules.Type<T> type, DimensionSelector[] dimensionSelectors) {
        *///?}
        for (DimensionSelector dimensionSelector : dimensionSelectors) {
            DimensionSelector.Builders getRule = dimensionSelector.builder();
            DimensionSelector.Builders setRule = dimensionSelector.builder();

            // gamerule <gamerule> <dimension_selector>
            //? if >= 1.21.11 {
            getRule.second().executes(ctx -> getRule(ctx, dimensionSelector.getLevels(ctx), rule));
             //?} else {
            /*getRule.second().executes(ctx -> getRule(ctx, dimensionSelector.getLevels(ctx), key));
            *///?}

            // gamerule <gamerule> <value> <dimension_selector>
            //? if >= 1.21.11 {
            setRule.second().executes(ctx -> setRule(ctx, dimensionSelector.getLevels(ctx), rule));
             //?} else {
            /*setRule.second().executes(ctx -> setRule(ctx, dimensionSelector.getLevels(ctx), key));
            *///?}

            // Support for dimension selectors with two arguments
            getRule.connect();
            setRule.connect();

            //? if >= 1.21.11 {
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
            //?} else {
            /*gamerule.then(
                    literal(key.getId())
                            .executes(ctx -> getRule(ctx, Lists.newArrayList(ctx.getSource().getServer().getAllLevels()), key))
                            .then(type.createArgument("value")
                                    .executes(ctx -> setRule(ctx, Lists.newArrayList(ctx.getSource().getServer().getAllLevels()), key))
                                    .then(setRule.first())
                            )
                            .then(getRule.first())
            );
            *///?}
        }
    }

    //? if >= 1.21.11 {
    static <T> int getRule(CommandContext<CommandSourceStack> commandContext, Collection<ServerLevel> levels, GameRule<T> rule) {
     //?} else {
    /*static <T extends GameRules.Value<T>> int getRule(CommandContext<CommandSourceStack> commandContext, Collection<ServerLevel> levels, GameRules.Key<T> key) {
        *///?}
        CommandSourceStack src = commandContext.getSource();
        if (levels.isEmpty()) {
            src.sendFailure(LocalizedMessage.localized("world-gamerules.commands.gamerule.set.empty"));
            return 0;
        }

        MutableComponent list = ComponentUtils.formatList(levels, LocalizedMessage.localized("world-gamerules.commands.gamerule.get.list.separator"), level -> {
            //? if >= 1.21.11 {
            T value = level.getGameRules().get(rule);
            return LocalizedMessage.builder("world-gamerules.commands.gamerule.get.list.element")
                    .addPlaceholder("value", rule.serialize(value))
                    .addPlaceholder("dimension", level.dimension().identifier().toString())
                    .build();
            //?} else {
            /*T value = level.getGameRules().getRule(key);
            return LocalizedMessage.builder("world-gamerules.commands.gamerule.get.list.element")
                    .addPlaceholder("value", value.toString())
                    .addPlaceholder("dimension", level.dimension().location().toString())
                    .build();
            *///?}
        });

        //? if >= 1.21.11 {
        src.sendSuccess(() -> LocalizedMessage.builder("world-gamerules.commands.gamerule.get")
                .addPlaceholder("gamerule", rule.id())
                .addPlaceholder("list", list)
                .build(), true);

        T value = commandContext.getSource().getLevel().getGameRules().get(rule);
        return rule.getCommandResult(value);
        //?} else {
        /*src.sendSuccess(() -> LocalizedMessage.builder("world-gamerules.commands.gamerule.get")
                .addPlaceholder("gamerule", key.getId())
                .addPlaceholder("list", list)
                .build(), true);
        return commandContext.getSource().getLevel().getGameRules().getRule(key).getCommandResult();
        *///?}
    }

    //? if >= 1.21.11 {
    static <T> int setRule(CommandContext<CommandSourceStack> commandContext, Collection<ServerLevel> levels, GameRule<T> rule) {
     //?} else {
    /*static <T extends GameRules.Value<T>> int setRule(CommandContext<CommandSourceStack> commandContext, Collection<ServerLevel> levels, GameRules.Key<T> key) {
        *///?}
        CommandSourceStack src = commandContext.getSource();
        if (levels.isEmpty()) {
            src.sendFailure(LocalizedMessage.localized("world-gamerules.commands.gamerule.set.empty"));
            return 0;
        }

        //? if >= 1.21.11 {
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
        //?} else {
        /*String stringValue = "";
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
        return commandContext.getSource().getLevel().getGameRules().getRule(key).getCommandResult();
        *///?}
    }
}