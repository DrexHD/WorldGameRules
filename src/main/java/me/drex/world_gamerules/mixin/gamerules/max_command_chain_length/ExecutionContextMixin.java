package me.drex.world_gamerules.mixin.gamerules.max_command_chain_length;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
//? if >= 1.21.11 {
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gamerules.GameRules;
        //?} else {
/*import net.minecraft.world.level.GameRules;
 *///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Commands.class)
public abstract class ExecutionContextMixin {

    @Redirect(
            method = "executeCommandInContext",
            at = @At(
                    value = "INVOKE",
                    //? if >= 1.21.11 {
                    target = "Lnet/minecraft/server/level/ServerLevel;getGameRules()Lnet/minecraft/world/level/gamerules/GameRules;"
                    //?} else {
                    /*target = "Lnet/minecraft/server/MinecraftServer;getGameRules()Lnet/minecraft/world/level/GameRules;"
                    *///?}
            )
    )
    private static GameRules useWorldGameRules(
            //? if >= 1.21.11 {
            ServerLevel level,
            //?} else {
            /*MinecraftServer instance,
             *///?}
            CommandSourceStack source) {
        return source.getLevel().getGameRules();
    }

}
