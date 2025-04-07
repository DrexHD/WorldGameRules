package me.drex.world_gamerules.duck;

import me.drex.world_gamerules.data.SavedWorldGameRules;
import me.drex.world_gamerules.data.SavedWorldLevelData;

public interface IServerLevel {
    SavedWorldGameRules worldGameRules$savedWorldGameRules();
    SavedWorldLevelData worldGameRules$savedWorldLevelData();
}
