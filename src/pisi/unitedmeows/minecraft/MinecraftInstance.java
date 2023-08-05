package pisi.unitedmeows.minecraft;

import net.minecraft.client.Minecraft;

public enum MinecraftInstance {
    INSTANCE;
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final String NAME = "Minecraft 1.8.10";
}
