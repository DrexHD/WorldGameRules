package me.drex.world_gamerules.mixin.gamerules.max_command_chain_length;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Commands.class)
public abstract class ExecutionContextMixin {

    @Redirect(
            method = "executeCommandInContext",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/MinecraftServer;getGameRules()Lnet/minecraft/world/level/GameRules;"
            )
    )
    private static GameRules useWorldGameRules(MinecraftServer instance, CommandSourceStack source) {
        return source.getLevel().getGameRules();
    }

}
