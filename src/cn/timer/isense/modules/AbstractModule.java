package cn.timer.isense.modules;

import cn.timer.isense.event.EventManager;
import cn.timer.isense.iSense;
import cn.timer.isense.modules.impl.Notification;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

public abstract class AbstractModule {
    @Getter
    private final String name, describe;
    @Getter
    @Setter
    private int key = 0;
    @Getter
    private boolean isEnabled;
    protected final Minecraft mc = Minecraft.getMinecraft();

    public AbstractModule(String name) {
        this.name = name;
        this.describe = null;
    }

    public AbstractModule(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }

    public AbstractModule(String name, String describe, int key) {
        this.name = name;
        this.describe = describe;
        this.key = key;
    }

    public AbstractModule(String name, int key) {
        this.name = name;
        this.describe = null;
        this.key = key;
    }

    public void setEnabled(boolean enabled) {
        if (isEnabled == enabled) return;
        isEnabled = enabled;
        if (isEnabled) {
            onEnabled();
            EventManager.instance.register(this);
        } else {
            onDisabled();
            EventManager.instance.unregister(this);
        }
        iSense.instance.getModuleManager().getByClass(Notification.class).
                add(
                        new cn.timer.isense.notification.Notification(name.replace(name.charAt(0), String.valueOf(name.charAt(0)).toUpperCase().charAt(0)) + (isEnabled ? " enabled" : " disabled"))
                );
    }


    public void toggle() {
        setEnabled(!isEnabled);
    }

    public void onEnabled() {
        //...
    }

    public void onDisabled() {
        //...
    }
}
