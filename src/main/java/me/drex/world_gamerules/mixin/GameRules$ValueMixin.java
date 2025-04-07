package me.drex.world_gamerules.mixin;

import me.drex.world_gamerules.duck.IServerLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRules.Value.class)
public abstract class GameRules$ValueMixin {

    @Inject(
        method = "onChanged",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/function/BiConsumer;accept(Ljava/lang/Object;Ljava/lang/Object;)V"
        )
    )
    public void onChanged(MinecraftServer minecraftServer, CallbackInfo ci) {
        for (ServerLevel level : minecraftServer.getAllLevels()) {
            ((IServerLevel) level).worldGameRules$savedWorldGameRules().setDirty();
        }
    }

}
