package me.drex.world_gamerules.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import eu.pb4.placeholders.api.PlaceholderContext;
import me.drex.message.api.LocalizedMessage;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameRules;

import java.util.Map;

public class WorldGameRuleCommand {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        final LiteralArgumentBuilder<CommandSourceStack> literal = Commands.literal("worldgamerule").requires(src -> Permissions.check(src, "world-gamerules.commands.worldgamerule", 2));
        GameRules.visitGameRuleTypes(new GameRules.GameRuleTypeVisitor() {
            @Override
            public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
                literal.then(
                    Commands.literal(key.getId()).then(
                        type.createArgument("value").executes(ctx -> setRule(ctx, key))
                    )
                );
            }
        });
        commandDispatcher.register(literal);
    }

    static <T extends GameRules.Value<T>> int setRule(CommandContext<CommandSourceStack> commandContext, GameRules.Key<T> key) {
        CommandSourceStack src = commandContext.getSource();
        T value = src.getLevel().getGameRules().getRule(key);
        value.setFromArgument(commandContext, "value");
        src.sendSuccess(() -> LocalizedMessage.localized("world-gamerules.commands.worldgamerule.set", Map.of(
            "gamerule", Component.literal(key.getId()),
            "value", Component.literal(value.toString())
        ), PlaceholderContext.of(src)), true);
        src.sendSuccess(() -> Component.translatable("commands.gamerule.set", key.getId(), value.toString()), true);
        return value.getCommandResult();
    }

}
