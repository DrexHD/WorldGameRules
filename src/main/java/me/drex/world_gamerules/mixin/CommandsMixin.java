package me.drex.world_gamerules.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Commands.class)
public abstract class CommandsMixin {

    @Redirect(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            //? if >= 1.21.4 {
            /*target = "Lnet/minecraft/server/commands/GameRuleCommand;register(Lcom/mojang/brigadier/CommandDispatcher;Lnet/minecraft/commands/CommandBuildContext;)V"
            *///?} else {
            target = "Lnet/minecraft/server/commands/GameRuleCommand;register(Lcom/mojang/brigadier/CommandDispatcher;)V"
            //?}
        )
    )
    private void disableVanillaGameRuleCommand(CommandDispatcher<CommandSourceStack> commandDispatcher/*? if >= 1.21.4 {*//*, CommandBuildContext commandBuildContext*//*?}*/) {
        // noop
    }

}
