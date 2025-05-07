package me.drex.world_gamerules.data;

import org.ladysnake.cca.api.v3.component.ComponentContainer;
import org.ladysnake.cca.api.v3.component.ComponentProvider;

public class CCASavedWorldLevelData extends SavedWorldLevelData implements ComponentProvider {

    @Override
    public ComponentContainer getComponentContainer() {
        if (this.parent instanceof ComponentProvider provider) {
            return provider.getComponentContainer();
        }
        return ComponentContainer.EMPTY;
    }
}
