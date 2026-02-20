package net.minecraftforge.fml.common.eventhandler;

public class Event {
    public enum Result {
        DENY,
        DEFAULT,
        ALLOW
    }

    private boolean canceled = false;
    private Result result = Result.DEFAULT;

    public boolean isCancelable() {
        return false;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        if (!isCancelable() && canceled)
            throw new IllegalArgumentException("Attempted to cancel a non-cancelable event");
        this.canceled = canceled;
    }

    public boolean hasResult() {
        return false;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
