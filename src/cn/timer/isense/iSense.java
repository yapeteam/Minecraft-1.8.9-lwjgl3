package cn.timer.isense;

import cn.timer.isense.event.EventManager;
import cn.timer.isense.modules.ModuleManager;
import lombok.Getter;

public class iSense {
    public static final iSense instance = new iSense();
    @Getter
    private ModuleManager moduleManager;

    public void StartUp() {
        this.moduleManager = new ModuleManager();
        moduleManager.initialize();
        EventManager.instance.register(moduleManager);
    }
}
