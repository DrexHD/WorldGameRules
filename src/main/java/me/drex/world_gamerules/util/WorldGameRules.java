package me.drex.world_gamerules.util;

import me.drex.world_gamerules.mixin.GameRulesValueAccessor;
import me.drex.world_gamerules.mixin.GameRulesAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.GameRules;

public class WorldGameRules extends GameRules {

    public WorldGameRules(FeatureFlagSet featureFlagSet) {
        super(featureFlagSet);
    }

    public WorldGameRules(FeatureFlagSet featureFlagSet, CompoundTag compoundTag) {
        super(featureFlagSet);
        loadFromCompoundTag(compoundTag);
    }

    private void loadFromCompoundTag(CompoundTag compoundTag) {
        ((GameRulesAccessor)this).getRules().forEach((key, value) -> {
            compoundTag.getString(key.getId()).ifPresent(data -> {
                ((GameRulesValueAccessor<?>)value).deserialize(data);
            });
        });
    }

}
