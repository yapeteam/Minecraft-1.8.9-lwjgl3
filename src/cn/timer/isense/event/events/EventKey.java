package cn.timer.isense.event.events;

import cn.timer.isense.event.Event;

public class EventKey extends Event {
    private final int key;

    public EventKey(int key) {
        super(null);
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
