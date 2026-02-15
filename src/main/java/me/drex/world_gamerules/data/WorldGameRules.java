package me.drex.world_gamerules.data;

import me.drex.world_gamerules.mixin.GameRulesAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.flag.FeatureFlagSet;
//? if >= 1.21.11 {
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleMap;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.Optional;
//?} else {
/*import net.minecraft.world.level.GameRules;
import me.drex.world_gamerules.mixin.GameRulesValueAccessor;
*///?}

public class WorldGameRules extends GameRules {

    public WorldGameRules(FeatureFlagSet featureFlagSet) {
        super(/*? if >= 1.21.4 {*/ featureFlagSet /*?}*/);
    }

    public WorldGameRules(FeatureFlagSet featureFlagSet, CompoundTag compoundTag) {
        super(/*? if >= 1.21.4 {*/ featureFlagSet /*?}*/);
        loadFromCompoundTag(compoundTag);
    }

    private void loadFromCompoundTag(CompoundTag compoundTag) {
        //? if >= 1.21.11 {
        GameRuleMap rules = ((GameRulesAccessor) this).getRules();
        for (GameRule<?> rule : rules.keySet()) {
            String id = rule.id();
            if (compoundTag.contains(id)) {
                this.loadRule(rules, rule, compoundTag.getString(id));
            }
        }
        //?} else {
        /*((GameRulesAccessor)this).getRules().forEach((key, value) -> {
            //? if >= 1.21.5 {
            compoundTag.getString(key.getId()).ifPresent(data -> {
                ((GameRulesValueAccessor<?>)value).deserialize(data);
            });
            //?} else {
            /^if (compoundTag.contains(key.getId())) {
                String data = compoundTag.getString(key.getId());
                ((GameRulesValueAccessor<?>)value).deserialize(data);
            }
            ^///?}
        });
        *///?}
    }

    //? if >= 1.21.11 {
    private <T> void setRule(GameRuleMap map, GameRule<T> rule, T value) {
        map.set(rule, value);
    }

    private <T> void loadRule(GameRuleMap rules, GameRule<T> rule, Optional<String> data) {
        data.flatMap(str -> rule.deserialize(str).result())
                .ifPresent(parsedValue -> this.setRule(rules, rule, parsedValue));
    }
    //?}

}
