package me.drex.world_gamerules.mixin.gamerules.do_daylight_cycle;

import me.drex.world_gamerules.duck.IServerLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements IServerLevel {

    protected ServerLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, supplier, bl, bl2, l, i);
    }

    @Shadow
    @Final
    private boolean tickTime;

    @Shadow
    public abstract void setDayTime(long l);

    @Inject(
        method = "tickTime",
        at = @At("HEAD")
    )
    public void perWorldDayTime(CallbackInfo ci) {
        if (!this.tickTime) {
            if (getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                this.setDayTime(this.worldGameRules$savedWorldLevelData().getDayTime() + 1L);
            }
        }
    }

    @Redirect(
        method = "setDayTime",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/storage/ServerLevelData;setDayTime(J)V"
        )
    )
    public void perWorldDayTime(ServerLevelData instance, long dayTime) {
        this.worldGameRules$savedWorldLevelData().setDayTime(dayTime);
    }

}
