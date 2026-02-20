package net.minecraftforge.fml.common.event;

import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class FMLStateEvent extends Event {
    private ModContainer modContainer;

    public ModContainer getModContainer() {
        return modContainer;
    }

    public void setModContainer(ModContainer modContainer) {
        this.modContainer = modContainer;
    }
}
