package me.drex.world_gamerules.util;

import net.fabricmc.loader.api.FabricLoader;

public class ModCompat {
    public static final boolean CARDINAL_COMPONENTS_LEVEL = FabricLoader.getInstance().isModLoaded("cardinal-components-level");
}
