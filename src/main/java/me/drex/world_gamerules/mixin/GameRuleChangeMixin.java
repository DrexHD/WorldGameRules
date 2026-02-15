package me.drex.world_gamerules.mixin;

import me.drex.world_gamerules.duck.IServerLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >= 1.21.11 {
import net.minecraft.world.level.gamerules.GameRule;
@Mixin(MinecraftServer.class)
public abstract class GameRuleChangeMixin {

    @Inject(
            method = "onGameRuleChanged",
            at = @At("TAIL")
    )
    public <T> void onGameRuleChanged(GameRule<T> gameRule, T value, CallbackInfo ci) {
        MinecraftServer server = (MinecraftServer) (Object) this;
        for (ServerLevel level : server.getAllLevels()) {
            ((IServerLevel) level).worldGameRules$savedWorldGameRules().setDirty();
        }
    }
}
//?} else {
/*import net.minecraft.world.level.GameRules;

@Mixin(GameRules.Value.class)
public abstract class GameRuleChangeMixin {

    @Inject(
            method = "onChanged",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/function/BiConsumer;accept(Ljava/lang/Object;Ljava/lang/Object;)V"
            )
    )
    public void onChanged(MinecraftServer minecraftServer, CallbackInfo ci) {
        for (ServerLevel level : minecraftServer.getAllLevels()) {
            ((IServerLevel) level).worldGameRules$savedWorldGameRules().setDirty();
        }
    }
}
*///?}