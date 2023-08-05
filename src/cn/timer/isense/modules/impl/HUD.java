package cn.timer.isense.modules.impl;

import cn.timer.isense.event.EventTarget;
import cn.timer.isense.event.events.EventRender2D;
import cn.timer.isense.modules.AbstractModule;
import org.lwjglx.input.Keyboard;

public class HUD extends AbstractModule {
    public HUD() {
        super("HUD", "Head Up Display", Keyboard.KEY_H);
    }
    @EventTarget
    private void onRender2D(EventRender2D e) {
        mc.fontRendererObj.drawString("iSense", 0, 0, -1, true);
    }
}
