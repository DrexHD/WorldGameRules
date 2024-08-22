package me.drex.world_gamerules;

import me.drex.world_gamerules.command.WorldGameRuleCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class WorldGameRulesMod implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> WorldGameRuleCommand.register(dispatcher));
    }

}
