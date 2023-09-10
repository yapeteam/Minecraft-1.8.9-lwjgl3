package cn.timer.isense.modules.impl;

import cn.timer.isense.event.EventTarget;
import cn.timer.isense.event.events.EventRender2D;
import cn.timer.isense.modules.ScriptModule;
import cn.timer.isense.script.Util;
import org.lwjglx.input.Keyboard;

/**
 * This is a Script Module, only for test, don't mind.
 */
public class Test extends ScriptModule {
    public Test() {
        super("Test", Util.readString(Test.class.getResourceAsStream("Test.spt")));
        setKey(Keyboard.KEY_J);
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        this.getScript().runBlock("render2D");
    }
}
