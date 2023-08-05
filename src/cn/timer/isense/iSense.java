package cn.timer.isense;

import cn.timer.isense.event.EventManager;
import cn.timer.isense.modules.ModuleManager;

public class iSense {
    public static iSense instance = new iSense();
    private static ModuleManager moduleManager;

    public void StartUp() {
        moduleManager = new ModuleManager();
        moduleManager.initialize();
        EventManager.instance.register(moduleManager);
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}
