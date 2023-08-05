package pisi.unitedmeows.meowlib.async;

public interface ITaskPool {

    void queue(Task<?> task);
    /* queue first */
    void queue_f(Task<?> task);
    void queue_w(Task<?> task, long after);
    Task<?> poll();
    int workerCount();
    void close();
    void setup();
}
