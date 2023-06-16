package me.drex.world_gamerules.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;

public class GlobalGameRuleCommand {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        final LiteralArgumentBuilder<CommandSourceStack> literal = Commands.literal("globalgamerule").requires(src -> Permissions.check(src, "world-gamerules.globalgamerule", 2));
        GameRules.visitGameRuleTypes(new GameRules.GameRuleTypeVisitor() {
            @Override
            public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
                literal.then(
                        Commands.literal(key.getId()).then(
                                type.createArgument("value").executes(commandContext -> setRule(commandContext, key))
                        )
                );
            }
        });
        commandDispatcher.register(literal);
    }

    static <T extends GameRules.Value<T>> int setRule(CommandContext<CommandSourceStack> commandContext, GameRules.Key<T> key) {
        CommandSourceStack src = commandContext.getSource();
        T value = null;
        for (ServerLevel level : src.getServer().getAllLevels()) {
            value = level.getGameRules().getRule(key);
            value.setFromArgument(commandContext, "value");
        }
        assert value != null;
        T finalValue = value;
        src.sendSuccess(() -> Component.translatable("commands.gamerule.set", key.getId(), finalValue.toString()), true);
        return value.getCommandResult();
    }

}
