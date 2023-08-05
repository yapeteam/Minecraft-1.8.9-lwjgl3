package cn.timer.isense.event;

public class Event {
    private TYPE type;
    private boolean isCancelled = false;

    public Event(TYPE type) {
        this.type = type;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }

    public enum TYPE {
        PRE, POST
    }
}
