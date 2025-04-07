package me.drex.world_gamerules.mixin.gamerules.do_daylight_cycle;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import me.drex.world_gamerules.duck.IServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.LevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelAccessor.class)
public interface LevelAccessorMixin {
    @ModifyReceiver(
        method = "dayTime",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/storage/LevelData;getDayTime()J"
        )
    )
    default LevelData perWorldDayTime(LevelData instance) {
        if (this instanceof IServerLevel duck) {
            return duck.worldGameRules$savedWorldLevelData();
        }
        return instance;
    }
}
