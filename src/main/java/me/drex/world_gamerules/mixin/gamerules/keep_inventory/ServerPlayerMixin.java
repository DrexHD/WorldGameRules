package me.drex.world_gamerules.mixin.gamerules.keep_inventory;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    @Redirect(
        method = "restoreFrom",
        at = @At(
            value = "INVOKE",
            //? if >= 1.21.4 {
            /*target = "Lnet/minecraft/server/level/ServerLevel;getGameRules()Lnet/minecraft/world/level/GameRules;"
            *///?} else {
            target = "Lnet/minecraft/world/level/Level;getGameRules()Lnet/minecraft/world/level/GameRules;"
            //?}
        )
    )
    public GameRules useOriginalWorldGameRules(@Coerce Object instance, ServerPlayer oldPlayer) {
        return oldPlayer.serverLevel().getGameRules();
    }

}
