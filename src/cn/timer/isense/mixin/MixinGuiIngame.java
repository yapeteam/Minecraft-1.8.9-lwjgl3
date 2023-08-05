package cn.timer.isense.mixin;

import cn.timer.isense.event.EventManager;
import cn.timer.isense.event.events.EventRender2D;
import net.minecraft.client.gui.GuiIngame;

@Mixin(clazz = GuiIngame.class)
public class MixinGuiIngame {
    @Inject(method = "renderGameOverlay", atMethod = "color", atPlace = Inject.Place.BEFORE)
    public void onRender2D() {
        float partialTicks = 0;
        EventManager.instance.call(new EventRender2D(partialTicks));
    }
}
