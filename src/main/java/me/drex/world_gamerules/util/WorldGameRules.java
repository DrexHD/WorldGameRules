package me.drex.world_gamerules.util;

import me.drex.world_gamerules.mixin.GameRulesValueAccessor;
import me.drex.world_gamerules.mixin.GameRulesAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.GameRules;

public class WorldGameRules extends GameRules {

    public WorldGameRules() {
    }

    public WorldGameRules(CompoundTag compoundTag) {
        super();
        loadFromCompoundTag(compoundTag);
    }

    private void loadFromCompoundTag(CompoundTag compoundTag) {
        ((GameRulesAccessor)this).getRules().forEach((key, value) -> {
            if (compoundTag.contains(key.getId())) {
                String data = compoundTag.getString(key.getId());
                ((GameRulesValueAccessor<?>)value).deserialize(data);
            }
        });
    }

}
