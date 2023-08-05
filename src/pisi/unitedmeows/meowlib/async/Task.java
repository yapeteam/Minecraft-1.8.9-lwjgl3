package pisi.unitedmeows.meowlib.async;

public abstract class Task<X> {

    private State state = State.IDLE;
    protected X result;
    private long startTime;
    private long timeSpent;
    private IAsyncAction action;

    public Task(IAsyncAction action) {
        this.action = action;
    }

    public void pre() {
        state = State.RUNNING;
        startTime = runningTime();
    }

    public abstract void run();


    public void post() {
        state = State.FINISHED;
        timeSpent = runningTime() - startTime;
    }



    public long runningTime() {
        return System.nanoTime() / 1000000L;
    }

    public long timeElapsed() {
        return runningTime() - startTime;
    }

    public long timeSpent() {
        return timeSpent;
    }

    public State state() {
        return state;
    }

    public X result() {
        return result;
    }

    public X await() {
        return (X) Async.await(this).result();
    }

    public void _return(Object value) {
        result = (X) value;
    }

    public enum State {
        FINISHED,
        RUNNING,
        ERROR,
        IDLE
    }
}
