package me.drex.world_gamerules.mixin;

import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.Value.class)
public interface GameRulesValueAccessor<T extends GameRules.Value<T>> {

    @Invoker("deserialize")
    void deserialize(String input);

}
