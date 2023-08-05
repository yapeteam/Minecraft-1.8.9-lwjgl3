package cn.timer.isense.modules.impl;

import cn.timer.isense.event.EventTarget;
import cn.timer.isense.event.events.EventRender2D;
import cn.timer.isense.modules.AbstractModule;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjglx.input.Keyboard;

import java.util.ArrayList;

public class Notification extends AbstractModule {
    public Notification() {
        super("Notification", Keyboard.KEY_K);
    }

    private final ArrayList<cn.timer.isense.notification.Notification> notifications = new ArrayList<>();

    @EventTarget
    private void onRender(EventRender2D e) {
        ScaledResolution sr = new ScaledResolution(mc);
        for (int i = 0; i < notifications.size(); i++) {
            cn.timer.isense.notification.Notification notification = notifications.get(i);
            notification.setTargetY(sr.getScaledHeight() - (cn.timer.isense.notification.Notification.getHeight() + 4) * (notifications.indexOf(notification) + 1));
            if (notification.getLeftTime() > 0) notification.render();
            else notifications.remove(notification);
        }
    }

    public void add(cn.timer.isense.notification.Notification notification) {
        ScaledResolution sr = new ScaledResolution(mc);
        notification.setTargetX(sr.getScaledWidth() - notification.getWidth() - 2);
        notification.setCurrentX(sr.getScaledWidth() + 2);
        notification.setCurrentY(sr.getScaledHeight() + 2);
        notifications.add(notification);
    }
}
