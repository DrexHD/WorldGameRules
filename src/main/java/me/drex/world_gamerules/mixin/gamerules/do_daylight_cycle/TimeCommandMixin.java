package me.drex.world_gamerules.mixin.gamerules.do_daylight_cycle;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.commands.TimeCommand;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(TimeCommand.class)
public abstract class TimeCommandMixin {
    @Redirect(
        method = {"setTime", "addTime"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/MinecraftServer;getAllLevels()Ljava/lang/Iterable;"
        )
    )
    private static Iterable<ServerLevel> perWorldTime(MinecraftServer instance, @Local(argsOnly = true) CommandSourceStack commandSourceStack) {
        return List.of(commandSourceStack.getLevel());
    }
}
