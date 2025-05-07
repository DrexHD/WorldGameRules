package me.drex.world_gamerules.mixin;

import me.drex.world_gamerules.data.SavedWorldGameRules;
import me.drex.world_gamerules.data.SavedWorldLevelData;
import me.drex.world_gamerules.duck.IServerLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements IServerLevel {

    protected ServerLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, bl, bl2, l, i);
    }

    @Unique
    private static final String FANTASY_PACKAGE = "xyz.nucleoid.fantasy";

    @Shadow
    @Final
    private ServerChunkCache chunkSource;

    @Shadow
    public abstract FeatureFlagSet enabledFeatures();

    @Mutable
    @Shadow
    @Final
    private ServerLevelData serverLevelData;
    @Unique
    private SavedWorldGameRules worldGameRules;


    @Inject(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/chunk/ChunkGeneratorStructureState;ensureStructuresGenerated()V"
        )
    )
    public void registerWorldGameRulesStorage(MinecraftServer minecraftServer, Executor executor, LevelStorageSource.LevelStorageAccess levelStorageAccess, ServerLevelData serverLevelData, ResourceKey<Level> resourceKey, LevelStem levelStem, ChunkProgressListener chunkProgressListener, boolean bl, long l, List<CustomSpawner> list, boolean bl2, @Nullable RandomSequences randomSequences, CallbackInfo ci) {
        worldGameRules = this.chunkSource.getDataStorage()
            .computeIfAbsent(new SavedData.Factory<>(
                () -> new SavedWorldGameRules(enabledFeatures()),
                (compoundTag, provider) -> SavedWorldGameRules.load(enabledFeatures(), compoundTag), null
            ), "gamerules");

        var className = this.getClass().getName();
        if (className.startsWith(FANTASY_PACKAGE)) {
            // Fantasy has its own logic to handle per world daylight cycle
            return;
        }

        // Replace serverLevelData
        var savedWorldLevelData = this.chunkSource.getDataStorage()
            .computeIfAbsent(new SavedData.Factory<>(
                SavedWorldLevelData::of,
                (compoundTag, provider) -> SavedWorldLevelData.load(compoundTag), null
            ), "world_level_data");
        savedWorldLevelData.setParent(this.serverLevelData);
        this.serverLevelData = savedWorldLevelData;
        ((LevelAccessor) this).setLevelData(savedWorldLevelData);
    }

    @Override
    public SavedWorldGameRules worldGameRules$savedWorldGameRules() {
        return this.worldGameRules;
    }

    /**
     * @author Drex
     * @reason Implement per world gamerules
     */
    @Overwrite
    public GameRules getGameRules() {
        return worldGameRules.getWorldGameRules();
    }
}
