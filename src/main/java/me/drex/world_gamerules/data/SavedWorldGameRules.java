package me.drex.world_gamerules.data;

import com.mojang.serialization.Codec;
import me.drex.world_gamerules.WorldGameRulesMod;
import me.drex.world_gamerules.mixin.GameRulesAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleMap;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SavedWorldGameRules extends SavedData {

    private final WorldGameRules worldGameRules;

    public static final Codec<SavedWorldGameRules> CODEC = CompoundTag.CODEC.xmap(
        tag -> load(FeatureFlagSet.of(), tag),
        SavedWorldGameRules::save
    );
    public static final SavedDataType<SavedWorldGameRules> TYPE = new SavedDataType<>(WorldGameRulesMod.id("gamerules"), SavedWorldGameRules::new, CODEC, null);

    public SavedWorldGameRules() {
        this(FeatureFlagSet.of());
    }

    private <T> String serializeForSave(GameRuleMap rules, GameRule<T> rule) {
        return rule.serialize(Objects.requireNonNull(rules.get(rule)));
    }

    public SavedWorldGameRules(FeatureFlagSet featureFlagSet) {
        this(new WorldGameRules(featureFlagSet));
    }

    private SavedWorldGameRules(WorldGameRules worldGameRules) {
        this.worldGameRules = worldGameRules;
    }

    public static SavedWorldGameRules load(FeatureFlagSet featureFlagSet, CompoundTag compoundTag) {
        return new SavedWorldGameRules(new WorldGameRules(featureFlagSet, compoundTag));
    }

    public @NotNull CompoundTag save() {
        CompoundTag compoundTag = new CompoundTag();
        GameRuleMap rules = ((GameRulesAccessor) worldGameRules).getRules();
        rules.keySet().forEach(rule -> compoundTag.putString(rule.id(), serializeForSave(rules, rule)));
        return compoundTag;
    }

    public WorldGameRules getWorldGameRules() {
        return worldGameRules;
    }
}
