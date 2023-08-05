package cn.timer.isense.event.events;

import cn.timer.isense.event.Event;

public class EventRender2D extends Event {
    private final float partialTicks;

    public EventRender2D(float partialTicks) {
        super(null);
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
