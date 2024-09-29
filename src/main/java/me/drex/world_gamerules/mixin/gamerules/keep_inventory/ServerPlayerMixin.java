package me.drex.world_gamerules.mixin.gamerules.keep_inventory;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    @Redirect(
            method = "restoreFrom",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;getGameRules()Lnet/minecraft/world/level/GameRules;"
            )
    )
    public GameRules useOriginalWorldGameRules(ServerLevel level, ServerPlayer oldPlayer) {
        return oldPlayer.serverLevel().getGameRules();
    }

}
