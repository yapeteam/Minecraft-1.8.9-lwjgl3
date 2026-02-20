package net.minecraftforge.fml.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class FMLClientHandler {
    private static final FMLClientHandler INSTANCE = new FMLClientHandler();

    private FMLClientHandler() {}

    public static FMLClientHandler instance() {
        return INSTANCE;
    }

    public boolean isLoading() {
        return false;
    }

    public void handleLoadingScreen(GuiScreen screen) {}

    public void trackBrokenTexture(ResourceLocation location, String message) {}

    public void trackMissingTexture(ResourceLocation location) {}
}
