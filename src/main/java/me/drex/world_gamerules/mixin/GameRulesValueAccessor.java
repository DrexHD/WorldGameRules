package me.drex.world_gamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;

//? if >= 1.21.11 {
import net.minecraft.world.level.Level;
@Mixin(Level.class)
public interface GameRulesValueAccessor {
    // dummy
}
//?} else {
/*import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.Value.class)
public interface GameRulesValueAccessor<T extends GameRules.Value<T>> {
    @Invoker("deserialize")
    void deserialize(String input);
}
*///?}