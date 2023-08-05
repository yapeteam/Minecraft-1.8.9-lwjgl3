package pisi.unitedmeows.meowlib.async;

public class Result<X> {

    protected X value;

    public Result(X val) {
        value = val;
    }

    public X value() {
        return value;
    }
}
