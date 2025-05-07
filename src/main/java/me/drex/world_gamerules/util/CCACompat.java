package me.drex.world_gamerules.util;

import me.drex.world_gamerules.data.CCASavedWorldLevelData;
import me.drex.world_gamerules.data.SavedWorldLevelData;
import net.fabricmc.loader.api.FabricLoader;

public class CCACompat {
    public static final boolean CARDINAL_COMPONENTS_LEVEL = FabricLoader.getInstance().isModLoaded("cardinal-components-level");

    public static SavedWorldLevelData create() {
        return new CCASavedWorldLevelData();
    }
}
