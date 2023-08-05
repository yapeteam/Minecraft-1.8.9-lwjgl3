package pisi.unitedmeows.meowlib.async;

import pisi.unitedmeows.meowlib.etc.CoID;
import pisi.unitedmeows.meowlib.etc.Tuple;

import java.util.ArrayList;
import java.util.List;

public class Future<X> {

    private X value;
    private CoID pointer;
    private Task task;
    private transient List<Tuple<Long, IAsyncAction>> afterTasks;

    public Future(CoID pointer) {
        this.pointer = pointer;
        task = null;
    }

    public Task<X> task() {
        if (task == null) {
            task = Async.task(pointer);
        }
        return task;
    }

    public void post() {
        if (afterTasks != null) {
            for (Tuple<Long, IAsyncAction> afterTask : afterTasks) {
                if (afterTask.getFirst() == 0) {
                    Async.async_f(afterTask.getSecond());
                } else {
                    Async.async_w(afterTask.getSecond(), afterTask.getFirst());
                }
            }
            afterTasks.clear();
        }
        task = Async.task(pointer);
        Async.removePointer(pointer);
    }

    public void after(IAsyncAction task) {
        if (afterTasks == null) {
            afterTasks = new ArrayList<>();
        }
        afterTasks.add(new Tuple<Long, IAsyncAction>(0L, task));
    }

    public void after_w(IAsyncAction task, long time) {
        if (afterTasks == null) {
            afterTasks = new ArrayList<>();
        }
        afterTasks.add(new Tuple<Long, IAsyncAction>(time, task));
    }


    public <x> x await() {
        return (x) Async.await(this);
    }


    public CoID pointer() {
        return pointer;
    }
}
