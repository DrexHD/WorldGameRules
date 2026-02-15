package me.drex.world_gamerules.mixin;

//? if >= 1.21.11 {
import net.minecraft.world.level.gamerules.GameRuleMap;
import net.minecraft.world.level.gamerules.GameRules;
//?} else {
/*import net.minecraft.world.level.GameRules;
 *///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(GameRules.class)
public interface GameRulesAccessor {

    //? if >= 1.21.11 {
    @Accessor
    GameRuleMap getRules();
    //?} else {
    /*@Accessor
    Map<GameRules.Key<?>, GameRules.Value<?>> getRules();
    *///?}

}
