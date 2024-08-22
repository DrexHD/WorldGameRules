package me.drex.world_gamerules.util;

import me.drex.world_gamerules.mixin.GameRulesAccessor;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

public class SavedWorldGameRules extends SavedData {

    private final WorldGameRules worldGameRules;

    public SavedWorldGameRules(FeatureFlagSet featureFlagSet) {
        this(new WorldGameRules(featureFlagSet));
    }

    private SavedWorldGameRules(WorldGameRules worldGameRules) {
        this.worldGameRules = worldGameRules;
    }

    public static SavedWorldGameRules load(FeatureFlagSet featureFlagSet, CompoundTag compoundTag) {
        return new SavedWorldGameRules(new WorldGameRules(featureFlagSet, compoundTag));
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        ((GameRulesAccessor) worldGameRules).getRules().forEach((key, value) -> compoundTag.putString(key.getId(), value.serialize()));
        return compoundTag;
    }

    @Override
    public boolean isDirty() {
        return true;
    }

    public WorldGameRules getWorldGameRules() {
        return worldGameRules;
    }
}
