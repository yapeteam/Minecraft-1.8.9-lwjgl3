package pisi.unitedmeows.meowlib.clazz;

import pisi.unitedmeows.meowlib.async.Async;
import pisi.unitedmeows.meowlib.async.Promise;

public abstract class updatable<X> implements IProperty<X> {

    protected X value;
    private Promise promise;

    public updatable(X _startValue, long interval) {
        this.value = _startValue;
        promise = Async.async_wloop(u-> { update(); }, interval);
    }

    public updatable(long interval) {
        promise = Async.async_loop(u-> { update(); }, interval);
    }

    public abstract void update();


    public Promise promise() {
        return promise;
    }

    @Deprecated
    @Override
    public void set(X value) {
        this.value = value;
    }

    @Override
    public X get() {
        return value;
    }

    public void stop() {
        promise().start();
    }

    public void startAgain() {
        promise().start();
    }
}
