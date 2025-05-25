package me.drex.world_gamerules.data;

import me.drex.world_gamerules.mixin.GameRulesValueAccessor;
import me.drex.world_gamerules.mixin.GameRulesAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.GameRules;

public class WorldGameRules extends GameRules {

    public WorldGameRules(FeatureFlagSet featureFlagSet) {
        super(/*? if >= 1.21.4 {*/ /*featureFlagSet *//*?}*/);
    }

    public WorldGameRules(FeatureFlagSet featureFlagSet, CompoundTag compoundTag) {
        super(/*? if >= 1.21.4 {*/ /*featureFlagSet *//*?}*/);
        loadFromCompoundTag(compoundTag);
    }

    private void loadFromCompoundTag(CompoundTag compoundTag) {
        ((GameRulesAccessor)this).getRules().forEach((key, value) -> {
            //? if >= 1.21.5 {
            /*compoundTag.getString(key.getId()).ifPresent(data -> {
                ((GameRulesValueAccessor<?>)value).deserialize(data);
            });
            *///?} else {
            if (compoundTag.contains(key.getId())) {
                String data = compoundTag.getString(key.getId());
                ((GameRulesValueAccessor<?>)value).deserialize(data);
            }
            //?}
        });
    }

}
