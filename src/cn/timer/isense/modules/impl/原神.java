package cn.timer.isense.modules.impl;

import cn.timer.isense.event.EventTarget;
import cn.timer.isense.event.events.EventRender2D;
import cn.timer.isense.modules.ScriptModule;
import cn.timer.isense.script.Util;
import org.lwjglx.input.Keyboard;

public class 原神 extends ScriptModule {
    public 原神() {
        super("原神", Util.readString(原神.class.getResourceAsStream("原神.spt")));
        setKey(Keyboard.KEY_J);
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        this.getScript().runBlock("render2D");
    }
}
