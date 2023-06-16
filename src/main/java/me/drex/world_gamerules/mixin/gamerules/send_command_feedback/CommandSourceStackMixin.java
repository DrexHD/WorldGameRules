package me.drex.world_gamerules.mixin.gamerules.send_command_feedback;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CommandSourceStack.class)
public abstract class CommandSourceStackMixin {

    @Shadow
    public abstract ServerLevel getLevel();

    @Redirect(
            method = "broadcastToAdmins",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/MinecraftServer;getGameRules()Lnet/minecraft/world/level/GameRules;"
            )
    )
    public GameRules useWorldGameRules(MinecraftServer server) {
        return this.getLevel().getGameRules();
    }

}
