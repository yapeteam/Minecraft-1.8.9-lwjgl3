package pisi.unitedmeows.meowlib.async;

import pisi.unitedmeows.meowlib.MeowLib;
import pisi.unitedmeows.meowlib.clazz.prop;
import pisi.unitedmeows.meowlib.etc.CoID;
import pisi.unitedmeows.meowlib.async.states.IState;
import pisi.unitedmeows.meowlib.etc.MLibSettings;
import pisi.unitedmeows.meowlib.thread.kThread;

import java.util.HashMap;

public class Async {
    
    private static HashMap<CoID, Task<?>> pointers;

    static {
        pointers = new HashMap<>();
    }

    public static Task<?> task(CoID id) {
        return pointers.get(id);
    }

    public static void _return(CoID uuid, Object result) {
        task(uuid)._return(result);
    }

    public static Promise async_when(IState when, IAsyncAction asyncAction, long checkInterval) {
        prop<Promise> promise = new prop<Promise>();
        promise.set(async_wloop(u -> {
            if (when.state()) {
                async_f(asyncAction);
                promise.get().stop();
            }
        }, checkInterval));
        return promise.get();
    }

    public static Promise async_while(IState state, IAsyncAction asyncAction, long loopInterval) {
        prop<Promise> promise = new prop<Promise>();
        promise.set(async_wloop(u -> {
            if (!state.state()) {
                promise.get().stop();
            }
            async_f(asyncAction);
        }, loopInterval));
        return promise.get();
    }

    public static <X> Future<X> async_f(IAsyncAction action) {
        // change this uuid alternative
        final CoID pointer = newPointer();
        Future<X> future = new Future<>(pointer);
        Task<?> task = new Task<Object>(action) {
            @Override
            public void run() {
                action.start(pointer);
                future.post();
            }
        };

        pointers.put(pointer, task);
        MeowLib.getTaskPool().queue_f(task);
        return future;
    }

    public static <X> Future<X> async(IAsyncAction action) {

        final CoID pointer = newPointer();

        Future<X> future = new Future<>(pointer);
        Task<?> task = new Task<Object>(action) {
            @Override
            public void run() {
               action.start(pointer);
               future.post();
            }
        };

        pointers.put(pointer, task);
        MeowLib.getTaskPool().queue(task);
        return future;
    }


    public static <X> Future<X> async_w(IAsyncAction action, final long after) {

        final CoID pointer = newPointer();

        Future<X> future = new Future<>(pointer);
        Task<?> task = new Task<Object>(action) {
            @Override
            public void run() {
                action.start(pointer);
                future.post();
            }
        };

        pointers.put(pointer, task);
        MeowLib.getTaskPool().queue_w(task, after);
        return future;
    }

    /* async_loop but waits before first call */
    public static Promise async_wloop(IAsyncAction action, final long repeatDelay) {
        final Promise promise = new Promise();
        Task<?> task = new Task<Object>(action) {
            @Override
            public void run() {
                if (promise.isValid()) {
                    action.start(null);
                    MeowLib.getTaskPool().queue_w(this, repeatDelay);
                }
            }
        };
        promise.start();

        MeowLib.getTaskPool().queue_w(task, repeatDelay);
        return promise;
    }

    /* same as wloop but starts manually */
    public static Promise async_wloop_w(IAsyncAction action, final long repeatDelay) {
        final Promise promise = new Promise();
        Task<?> task = new Task<Object>(action) {
            @Override
            public void run() {
                if (promise.isValid()) {
                    action.start(null);
                    MeowLib.getTaskPool().queue_w(this, repeatDelay);
                }
            }
        };

        MeowLib.getTaskPool().queue_w(task, repeatDelay);
        return promise;
    }

    public static Promise async_loop(IAsyncAction action, final long repeatDelay, final long lifeTime) {
        final Promise promise = async_loop(action, repeatDelay);
        async_w((u) -> promise.stop(), lifeTime);
        return promise;
    }


    public static Promise async_loop(IAsyncAction action, final long repeatDelay) {
        final Promise promise = new Promise();
        Task<?> task = new Task<Object>(action) {
            @Override
            public void run() {
                if (promise.isValid()) {
                    action.start(null);
                    MeowLib.getTaskPool().queue_w(this, repeatDelay);
                }
            }
        };
        promise.start();

        MeowLib.getTaskPool().queue(task);
        return promise;
    }

    public static Promise async_loop_times(IAsyncAction loop, IAsyncAction finalAction, final long repeatDelay, final int repeatTime) {
        final Promise promise = async_loop(loop, repeatDelay);

        async_w((u)->{
            promise.stop();
            async_f(finalAction);
        }, repeatDelay * repeatTime);
        return promise;
    }

    public static Promise async_loop_times(IAsyncAction loop, final long repeatDelay, final int repeatTime) {
        final Promise promise = async_loop(loop, repeatDelay);
        async_w((u)->{
            promise.stop();
        }, repeatDelay * repeatTime);
        return promise;
    }

    public static Promise async_loop_times_w(IAsyncAction loop, final long repeatDelay, final int repeatTime) {
        final Promise promise = async_loop_m(loop, repeatDelay);
        async_w(u -> {
            promise.start();
        }, repeatDelay);
        async_w((u)->{
            promise.stop();
        }, repeatDelay * repeatTime);
        return promise;
    }


    public static Promise async_loop_till(IAsyncAction loop, final long repeatDelay, final int till) {
        final Promise promise = async_loop(loop, repeatDelay);
        async_w((u) -> {
            promise.stop();
        }, till);
        return promise;
    }

    public static Promise async_loop_till(IAsyncAction loop, IAsyncAction finalAction, final long repeatDelay, final int till) {
        final Promise promise = async_loop(loop, repeatDelay);

        async_w((u) -> {
            promise.stop();
            async_f(finalAction);
        }, till);
        return promise;
    }
    public static Promise async_loop_till_w(IAsyncAction loop, final long repeatDelay, final int till) {
        final Promise promise = async_loop_m(loop, repeatDelay);
        async_w(u -> {
            promise.start();
        }, repeatDelay);
        async_w((u) -> {
            promise.stop();
        }, till);
        return promise;
    }

    public static Promise async_loop_till_w(IAsyncAction loop, IAsyncAction finalAction, final long repeatDelay, final int till) {
        final Promise promise = async_loop_m(loop, repeatDelay);
        async_w(u -> {
            promise.start();
        }, repeatDelay);
        async_w((u) -> {
            async_f(finalAction);
            promise.stop();
        }, till);
        return promise;
    }

    /* same as async_loop but starts manually */
    public static Promise async_loop_m(IAsyncAction action, final long repeatDelay) {
        final Promise promise = new Promise();
        Task<?> task = new Task<Object>(action) {
            @Override
            public void run() {
                if (promise.isValid()) {
                    action.start(null);
                    MeowLib.getTaskPool().queue_w(this, repeatDelay);
                }
            }
        };

        MeowLib.getTaskPool().queue(task);
        return promise;
    }





    public static <X> X await(Future<?> future) {
        return (X) await(pointers.get(future.pointer())).result();
    }

    public static Task<?> await(Task<?> task) {
        long checkTime = (long)MeowLib.mLibSettings().get(MLibSettings.ASYNC_AWAIT_CHECK_DELAY).getValue();
        while (task.state() == Task.State.RUNNING || task.state() == Task.State.IDLE) {
            kThread.sleep(checkTime);
        }
        return task;
    }


    /* this code shouldn't exists but looks cool */
    public static <X> Future<X> async_t(IAsyncAction action) {
        final CoID pointer = newPointer();
        Task<?> task = new Task<Object>(action) {
            @Override
            public void run() {
                action.start(pointer);
            }
        };
        Future<X> future = new Future<>(pointer);
        pointers.put(pointer, task);
        new Thread(()-> {
            task.pre();
            task.run();
            task.post();
            future.post();
        }).start();

        return future;
    }

    private static CoID newPointer() {
        CoID pointer;
        do {
            pointer = CoID.generate();
        } while (pointers.containsKey(pointer));
        return pointer;
    }

    public static void removePointer(CoID coid) {
        pointers.remove(coid);
    }

    public static int pointerCount() {
        return pointers.size();
    }


}
