package me.drex.world_gamerules.mixin.gamerules.keep_inventory;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    @Redirect(
        method = "restoreFrom",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;getGameRules()Lnet/minecraft/world/level/gamerules/GameRules;"
        )
    )
    public GameRules useOriginalWorldGameRules(ServerLevel instance, @Local(argsOnly = true) ServerPlayer oldPlayer) {
        return oldPlayer.level().getGameRules();
    }

}
