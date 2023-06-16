package me.drex.world_gamerules.mixin.gamerules.max_command_chain_length;

import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.server.ServerFunctionManager$ExecutionContext"})
public abstract class ExecutionContextMixin {

    @Redirect(
            method = "delayFunctionCall",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/ServerFunctionManager;getCommandLimit()I"
            )
    )
    public int modifyCommandLimit(ServerFunctionManager functionManager, CommandFunction commandFunction, CommandSourceStack src) {
        return src.getLevel().getGameRules().getInt(GameRules.RULE_MAX_COMMAND_CHAIN_LENGTH);
    }

    @Redirect(
            method = "runTopCommand",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/ServerFunctionManager;getCommandLimit()I"
            )
    )
    public int modifyCommandLimit2(ServerFunctionManager functionManager, CommandFunction commandFunction, CommandSourceStack src) {
        return src.getLevel().getGameRules().getInt(GameRules.RULE_MAX_COMMAND_CHAIN_LENGTH);
    }

}
