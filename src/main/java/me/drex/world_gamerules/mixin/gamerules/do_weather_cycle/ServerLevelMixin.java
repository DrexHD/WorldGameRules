package me.drex.world_gamerules.mixin.gamerules.do_weather_cycle;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.drex.world_gamerules.duck.IServerLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements IServerLevel {

    protected ServerLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, supplier, bl, bl2, l, i);
    }

    @WrapOperation(
        method = "advanceWeatherCycle",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/players/PlayerList;broadcastAll(Lnet/minecraft/network/protocol/Packet;)V"
        )
    )
    public void perWorldWeather(PlayerList instance, Packet<?> packet, Operation<Void> original) {
        instance.broadcastAll(packet, this.dimension());
    }

    @Redirect(
        method = {"setWeatherParameters", "advanceWeatherCycle"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;setClearWeatherTime(I)V")
    )
    public void perWorldWeatherClear(ServerLevelData instance, int i) {
        worldGameRules$savedWorldLevelData().setClearWeatherTime(i);
    }

    @ModifyReceiver(
        method = {"setWeatherParameters", "advanceWeatherCycle", "resetWeatherCycle"},
        at = {
            @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;setClearWeatherTime(I)V"),
            @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;setRainTime(I)V"),
            @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;setThunderTime(I)V"),
        }
    )
    public ServerLevelData perWorldWeatherSetInt(ServerLevelData original, int i) {
        return worldGameRules$savedWorldLevelData();
    }

    @ModifyReceiver(
        method = {"setWeatherParameters", "advanceWeatherCycle", "resetWeatherCycle"},
        at = {
            @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;setRaining(Z)V"),
            @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;setThundering(Z)V"),
        }
    )
    public ServerLevelData perWorldWeatherSetBoolean(ServerLevelData original, boolean b) {
        return worldGameRules$savedWorldLevelData();
    }

    @ModifyReceiver(
        method = {"setWeatherParameters", "advanceWeatherCycle", "resetWeatherCycle"},
        at = {
            @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;getClearWeatherTime()I"),
            @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;getThunderTime()I"),
            @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;getRainTime()I")
        }
    )
    public ServerLevelData perWorldWeather(ServerLevelData original) {
        return worldGameRules$savedWorldLevelData();
    }

    @ModifyReceiver(
        method = "advanceWeatherCycle",
        at = {
            @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/WritableLevelData;isRaining()Z")
        }
    )
    public WritableLevelData perWorldWeather2(WritableLevelData instance) {
        return worldGameRules$savedWorldLevelData();
    }

}
