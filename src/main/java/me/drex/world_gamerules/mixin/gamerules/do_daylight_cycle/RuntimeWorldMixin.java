package me.drex.world_gamerules.mixin.gamerules.do_daylight_cycle;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(targets = "xyz.nucleoid.fantasy.RuntimeWorld")
public abstract class RuntimeWorldMixin extends ServerLevel {

    public RuntimeWorldMixin(MinecraftServer minecraftServer, Executor executor, LevelStorageSource.LevelStorageAccess levelStorageAccess, ServerLevelData serverLevelData, ResourceKey<Level> resourceKey, LevelStem levelStem, ChunkProgressListener chunkProgressListener, boolean bl, long l, List<CustomSpawner> list, boolean bl2, @Nullable RandomSequences randomSequences) {
        super(minecraftServer, executor, levelStorageAccess, serverLevelData, resourceKey, levelStem, chunkProgressListener, bl, l, list, bl2, randomSequences);
    }

    @Redirect(
            method = "tickTime",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/storage/WritableLevelData;getGameRules()Lnet/minecraft/world/level/GameRules;"
            )
    )
    public GameRules perWorldDayTime(@Coerce Object instance) {
        return this.getGameRules();
    }
}
