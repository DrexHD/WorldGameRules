package me.drex.world_gamerules.mixin;

import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(GameRules.class)
public interface GameRulesAccessor {

    @Accessor
    Map<GameRules.Key<?>, GameRules.Value<?>> getRules();

}
