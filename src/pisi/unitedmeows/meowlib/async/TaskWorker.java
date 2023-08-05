package pisi.unitedmeows.meowlib.async;

import pisi.unitedmeows.meowlib.MeowLib;
import pisi.unitedmeows.meowlib.etc.MLibSettings;

public class TaskWorker extends Thread {

    private boolean running;
    private Task<?> runningTask;
    private ITaskPool pool;
    private long lastWork;

    private static long BUSY_DELAY = (long) MeowLib.mLibSettings().get(MLibSettings.ASYNC_WORKER_BUSY).getValue();

    public TaskWorker(ITaskPool owner) {
        pool = owner;
        lastWork = curTime();
        setDaemon(true);
    }

    @Override
    public void run() {
        while (running) {
            try {
                runningTask = pool.poll();
                if (runningTask == null) { //todo remove???
                    return;
                }
                runningTask.pre();
                runningTask.run();
                runningTask.post();
                runningTask = null;
                lastWork =  curTime();
            } catch (NoSuchMethodError | NullPointerException ex) {
                runningTask = null;
            }
        }
    }


    private long curTime() {
        return System.nanoTime() / 1000000L;
    }

    public long lastWorkTimeElapsed() {
        return curTime() - lastWork;
    }


    public Task getRunningTask() {
        return runningTask;
    }

    public boolean isBusy() {
        if (isWorking()) {
            return runningTask.timeElapsed() >= BUSY_DELAY;
        }
        return false;
    }



    public boolean isWorking() {
        return getRunningTask() != null;
    }

    public boolean isFree() {
        return !isWorking();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void stopWorker() {
        running = false;
    }

    public void startWorker() {
        running = true;
        start();
    }
}
