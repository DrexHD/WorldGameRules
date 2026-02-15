package me.drex.world_gamerules.data;

import com.mojang.serialization.Codec;
import me.drex.world_gamerules.mixin.GameRulesAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.flag.FeatureFlagSet;
//? if >= 1.21.11 {
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleMap;
//?} else {
/*import java.util.function.Function;
 *///?}
import net.minecraft.world.level.saveddata.SavedData;
//? if >= 1.21.5 {
import net.minecraft.world.level.saveddata.SavedDataType;
//?} else {
/*import net.minecraft.core.HolderLookup;
 *///?}
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SavedWorldGameRules extends SavedData {

    private final WorldGameRules worldGameRules;

    //? if >= 1.21.11 {
    public static final Codec<SavedWorldGameRules> CODEC = CompoundTag.CODEC.xmap(
            tag -> load(FeatureFlagSet.of(), tag),
            SavedWorldGameRules::save
    );
    public static final SavedDataType<SavedWorldGameRules> TYPE = new SavedDataType<>("gamerules", SavedWorldGameRules::new, CODEC, null);

    public SavedWorldGameRules() {
        this(FeatureFlagSet.of());
    }

    private <T> String serializeForSave(GameRuleMap rules, GameRule<T> rule) {
        return rule.serialize(Objects.requireNonNull(rules.get(rule)));
    }

    //?} else if >= 1.21.5 {
    /*public static final Function<Context, Codec<SavedWorldGameRules>> CODEC = context -> CompoundTag.CODEC.xmap(
        tag -> load(context.levelOrThrow().enabledFeatures(), tag),
        SavedWorldGameRules::save);
    public static final SavedDataType<SavedWorldGameRules> TYPE = new SavedDataType<>("gamerules", SavedWorldGameRules::new, CODEC, null);

    public SavedWorldGameRules(SavedData.Context context) {
        this.worldGameRules = new WorldGameRules(context.levelOrThrow().enabledFeatures());
    }
    *///?}

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
        //? if >= 1.21.11 {
        GameRuleMap rules = ((GameRulesAccessor) worldGameRules).getRules();
        rules.keySet().forEach(rule -> compoundTag.putString(rule.id(), serializeForSave(rules, rule)));
        //?} else {
        /*((GameRulesAccessor) worldGameRules).getRules().forEach((key, value) -> compoundTag.putString(key.getId(), value.serialize()));
         *///?}
        return compoundTag;
    }

    public WorldGameRules getWorldGameRules() {
        return worldGameRules;
    }
}
