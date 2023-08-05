package cn.timer.isense.mixin;

import cn.timer.isense.event.EventManager;
import cn.timer.isense.event.events.EventKey;
import cn.timer.isense.event.events.EventTick;
import cn.timer.isense.iSense;
import net.minecraft.client.Minecraft;

@Mixin(clazz = Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "runTick", at = "Head")
    public void onTick() {
        EventManager.instance.call(new EventTick());
    }

    @Inject(method = "runTick", atMethod = "switchUseShader", atPlace = Inject.Place.BEFORE)
    public void onKey() {
        int k = 0;
        EventManager.instance.call(new EventKey(k));
    }

    @Inject(method = "startGame", at = "Return")
    public void StartUp() {
        iSense.instance.StartUp();
    }
}
