package net.minecraftforge.fml.common;

import net.minecraft.crash.CrashReport;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class FMLCommonHandler {
    private static final FMLCommonHandler INSTANCE = new FMLCommonHandler();

    private FMLCommonHandler() {}

    public static FMLCommonHandler instance() {
        return INSTANCE;
    }

    public List<String> getBrandings(boolean includeMCVersion) {
        List<String> brandings = new ArrayList<>();
        int count = Loader.instance().getActiveModList().size();
        brandings.add("Forge Mods: " + count);
        return brandings;
    }

    public Side getEffectiveSide() {
        return Side.CLIENT;
    }

    public void handleServerAboutToStart(MinecraftServer server) {}

    public void handleServerStarting(MinecraftServer server) {}

    public void enhanceCrashReport(CrashReport report, Object a, Object b, Object c) {}

    public void callFuture(Runnable task) {
        task.run();
    }
}
