package me.drex.world_gamerules.mixin;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.commands.GameRuleCommand;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRuleCommand.class)
public abstract class GameRuleCommandMixin {

    @Redirect(
            method = "setRule",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/MinecraftServer;getGameRules()Lnet/minecraft/world/level/GameRules;"
            )
    )
    private static GameRules setWorldGameRules(MinecraftServer server, CommandContext<CommandSourceStack> ctx) {
        return ctx.getSource().getLevel().getGameRules();
    }

    @Redirect(
            method = "queryRule",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/MinecraftServer;getGameRules()Lnet/minecraft/world/level/GameRules;"
            )
    )
    private static GameRules queryWorldGameRules(MinecraftServer server, CommandSourceStack src) {
        return src.getLevel().getGameRules();
    }

}
