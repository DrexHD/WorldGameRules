package me.drex.world_gamerules;

import me.drex.world_gamerules.command.WorldGameRuleCommand;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class WorldGameRulesMod implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> WorldGameRuleCommand.register(dispatcher));
    }

}
