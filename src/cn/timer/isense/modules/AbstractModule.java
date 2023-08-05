package cn.timer.isense.modules;

import cn.timer.isense.event.EventManager;
import cn.timer.isense.iSense;
import cn.timer.isense.modules.impl.Notification;
import net.minecraft.client.Minecraft;

public abstract class AbstractModule {
    private final String name, describe;
    private int key = 0;
    private boolean isEnable;
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

    public String getName() {
        return name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setEnable(boolean enable) {
        if (isEnable == enable) return;
        isEnable = enable;
        if (isEnable) {
            EventManager.instance.register(this);
        } else {
            EventManager.instance.unregister(this);
        }
        iSense.instance.getModuleManager().getByClass(Notification.class).
                add(
                        new cn.timer.isense.notification.Notification(name.replace(name.charAt(0), String.valueOf(name.charAt(0)).toUpperCase().charAt(0)) + (isEnable ? " enabled" : " disabled"))
                );
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void toggle() {
        setEnable(!isEnable);
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
