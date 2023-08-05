package pisi.unitedmeows.meowlib.clazz;

public class prop<X> implements IProperty<X> {

    protected X value; /* change this to some optional variable */

    public prop() { }

    public prop(X _startObject) {
        value = _startObject;
    }

    @Override
    public void set(X value) {
        this.value = value;
    }

    @Override
    public X get() {
        return value;
    }
}
