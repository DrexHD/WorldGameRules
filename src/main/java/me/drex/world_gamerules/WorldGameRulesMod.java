package me.drex.world_gamerules;

import me.drex.world_gamerules.command.WorldGameRuleCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.resources.Identifier;

public class WorldGameRulesMod implements ModInitializer {

    // TODO 26.1
    // Allow advance_time and advance_weather to work per dimension
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, context, environment) -> WorldGameRuleCommand.register(dispatcher));
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath("world-gamerules", path);
    }
}
