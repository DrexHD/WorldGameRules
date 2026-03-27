package me.drex.world_gamerules.mixin;

import net.minecraft.world.level.gamerules.GameRuleMap;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRules.class)
public interface GameRulesAccessor {

    @Accessor
    GameRuleMap getRules();

}
