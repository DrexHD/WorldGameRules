package me.drex.world_gamerules.mixin;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.impl.gamerule.EnumRuleCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
//? if >= 1.21.11 {
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gamerules.GameRules;
        //?} else {
/*import net.minecraft.world.level.GameRules;
 *///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnumRuleCommand.class)
public abstract class EnumRuleCommandMixin {

    @Redirect(
            method = "executeAndSetEnum",
            at = @At(
                    value = "INVOKE",
                    //? if >= 1.21.11 {
                    target = "Lnet/minecraft/server/level/ServerLevel;getGameRules()Lnet/minecraft/world/level/gamerules/GameRules;"
                    //?} else {
                    /*target = "Lnet/minecraft/server/MinecraftServer;getGameRules()Lnet/minecraft/world/level/GameRules;"
                    *///?}
            )
    )
    private static GameRules setWorldGameRules(
            //? if >= 1.21.11 {
            ServerLevel level,
            //?} else {
            /*MinecraftServer server,
             *///?}
            CommandContext<CommandSourceStack> ctx
    ) {
        return ctx.getSource().getLevel().getGameRules();
    }

}
