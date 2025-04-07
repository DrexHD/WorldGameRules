package me.drex.world_gamerules.mixin.gamerules.do_weather_cycle;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.commands.WeatherCommand;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WeatherCommand.class)
public abstract class WeatherCommandMixin {

    @Redirect(
        method = {"setClear", "setRain", "setThunder"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/MinecraftServer;overworld()Lnet/minecraft/server/level/ServerLevel;"
        )
    )
    private static ServerLevel perWorldWeather(MinecraftServer server, @Local(argsOnly = true) CommandSourceStack source) {
        return source.getLevel();
    }

}
