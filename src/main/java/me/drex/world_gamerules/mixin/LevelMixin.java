package me.drex.world_gamerules.mixin;

//? if < 1.21.4 {

/*import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.drex.world_gamerules.duck.IServerLevel;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.level.Level;

@Mixin(Level.class)
public abstract class LevelMixin {
    @WrapMethod(method = "getGameRules")
    public GameRules getWorldGameRules(Operation<GameRules> original) {
        if (this instanceof IServerLevel iServerLevel) {
            return iServerLevel.worldGameRules$savedWorldGameRules().getWorldGameRules();
        }
        return original.call();
    }
}

*///?} else {

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MinecraftServer.class)
public abstract class LevelMixin {

}
//?}