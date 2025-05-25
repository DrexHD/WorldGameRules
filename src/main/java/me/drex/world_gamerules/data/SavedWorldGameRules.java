package me.drex.world_gamerules.data;

import com.mojang.serialization.Codec;
import me.drex.world_gamerules.mixin.GameRulesAccessor;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.saveddata.SavedData;
//? if >= 1.21.5 {
import net.minecraft.world.level.saveddata.SavedDataType;
//?}
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class SavedWorldGameRules extends SavedData {

    private final WorldGameRules worldGameRules;
    //? if >= 1.21.5 {
    public static final Function<Context, Codec<SavedWorldGameRules>> CODEC = context -> CompoundTag.CODEC.xmap(
        tag -> load(context.levelOrThrow().enabledFeatures(), tag),
        SavedWorldGameRules::save);
    public static final SavedDataType<SavedWorldGameRules> TYPE = new SavedDataType<>("gamerules", SavedWorldGameRules::new, CODEC, null);

    public SavedWorldGameRules(SavedData.Context context) {
        this.worldGameRules = new WorldGameRules(context.levelOrThrow().enabledFeatures());
    }
    //?}

    public SavedWorldGameRules(FeatureFlagSet featureFlagSet) {
        this(new WorldGameRules(featureFlagSet));
    }

    private SavedWorldGameRules(WorldGameRules worldGameRules) {
        this.worldGameRules = worldGameRules;
    }

    public static SavedWorldGameRules load(FeatureFlagSet featureFlagSet, CompoundTag compoundTag) {
        return new SavedWorldGameRules(new WorldGameRules(featureFlagSet, compoundTag));
    }

    //? if >= 1.21.5 {
    public @NotNull CompoundTag save() {
        CompoundTag compoundTag = new CompoundTag();
    //?} else {
    /*@Override
    public @NotNull CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
    *///?}
        ((GameRulesAccessor) worldGameRules).getRules().forEach((key, value) -> compoundTag.putString(key.getId(), value.serialize()));
        return compoundTag;
    }

    public WorldGameRules getWorldGameRules() {
        return worldGameRules;
    }
}
