package me.drex.world_gamerules.mixin.gamerules.do_daylight_cycle;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Local;
import me.drex.world_gamerules.duck.IServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.LevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MapItemSavedData.class)
public abstract class MapItemSavedDataMixin {

    @ModifyReceiver(
        method = "calculateRotation",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/LevelData;getDayTime()J")
    )
    public LevelData perWorldDayTime(LevelData instance, @Local(argsOnly = true) LevelAccessor accessor) {
        if (accessor instanceof IServerLevel duck) {
            return duck.worldGameRules$savedWorldLevelData();
        }
        return instance;
    }

}
