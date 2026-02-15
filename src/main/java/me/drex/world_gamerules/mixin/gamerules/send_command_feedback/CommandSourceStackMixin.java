package me.drex.world_gamerules.mixin.gamerules.send_command_feedback;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
//? if >= 1.21.11 {
import net.minecraft.world.level.gamerules.GameRules;
//?} else {
/*import net.minecraft.world.level.GameRules;
 *///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CommandSourceStack.class)
public abstract class CommandSourceStackMixin {

    @Shadow
    public abstract ServerLevel getLevel();

    @Redirect(
            method = "broadcastToAdmins",
            at = @At(
                    value = "INVOKE",
                    //? if >= 1.21.11 {
                    target = "Lnet/minecraft/server/level/ServerLevel;getGameRules()Lnet/minecraft/world/level/gamerules/GameRules;"
                    //?} else {
                    /*target = "Lnet/minecraft/server/MinecraftServer;getGameRules()Lnet/minecraft/world/level/GameRules;"
                    *///?}
            )
    )
    public GameRules useWorldGameRules(
            //? if >= 1.21.11 {
            ServerLevel level
             //?} else {
            /*MinecraftServer server
            *///?}
    ) {
        return this.getLevel().getGameRules();
    }

}
