package me.drex.world_gamerules.mixin.gamerules.do_daylight_cycle;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.drex.world_gamerules.duck.IServerLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Level.class)
public abstract class LevelMixin {

    @WrapOperation(
        method = "getDayTime",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/storage/WritableLevelData;getDayTime()J"
        )
    )
    public long perWorldDayTime(WritableLevelData instance, Operation<Long> original) {
        if (this instanceof IServerLevel duck) {
            return duck.worldGameRules$savedWorldLevelData().getDayTime();
        }
        return original.call(instance);
    }

}
