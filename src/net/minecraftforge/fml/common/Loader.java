package net.minecraftforge.fml.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Loader {
    private static final Loader INSTANCE = new Loader();
    private final List<ModContainer> activeMods = new ArrayList<>();
    private final Map<String, ModContainer> indexedMods = new LinkedHashMap<>();

    private Loader() {}

    public static Loader instance() {
        return INSTANCE;
    }

    public List<ModContainer> getActiveModList() {
        return activeMods;
    }

    public boolean isModLoaded(String modId) {
        return indexedMods.containsKey(modId);
    }

    public Map<String, ModContainer> getIndexedModList() {
        return indexedMods;
    }

    public void registerMod(ModContainer mod) {
        activeMods.add(mod);
        indexedMods.put(mod.getModId(), mod);
    }
}
