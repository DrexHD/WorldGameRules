package me.drex.world_gamerules.data;

import me.drex.world_gamerules.mixin.GameRulesAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleMap;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.Optional;

public class WorldGameRules extends GameRules {

    public WorldGameRules(FeatureFlagSet featureFlagSet) {
        super(featureFlagSet);
    }

    public WorldGameRules(FeatureFlagSet featureFlagSet, CompoundTag compoundTag) {
        super(featureFlagSet);
        loadFromCompoundTag(compoundTag);
    }

    private void loadFromCompoundTag(CompoundTag compoundTag) {
        GameRuleMap rules = ((GameRulesAccessor) this).getRules();
        for (GameRule<?> rule : rules.keySet()) {
            String id = rule.id();
            if (compoundTag.contains(id)) {
                this.loadRule(rules, rule, compoundTag.getString(id));
            }
        }
    }

    private <T> void setRule(GameRuleMap map, GameRule<T> rule, T value) {
        map.set(rule, value);
    }

    private <T> void loadRule(GameRuleMap rules, GameRule<T> rule, Optional<String> data) {
        data.flatMap(str -> rule.deserialize(str).result())
            .ifPresent(parsedValue -> this.setRule(rules, rule, parsedValue));
    }
}
