package me.drex.world_gamerules.mixin;

import me.drex.world_gamerules.duck.IServerLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.level.gamerules.GameRule;
@Mixin(MinecraftServer.class)
public abstract class GameRuleChangeMixin {

    @Inject(
            method = "onGameRuleChanged",
            at = @At("TAIL")
    )
    public <T> void onGameRuleChanged(GameRule<T> gameRule, T value, CallbackInfo ci) {
        MinecraftServer server = (MinecraftServer) (Object) this;
        for (ServerLevel level : server.getAllLevels()) {
            ((IServerLevel) level).worldGameRules$savedWorldGameRules().setDirty();
        }
    }
}