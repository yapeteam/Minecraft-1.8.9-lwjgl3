package pisi.unitedmeows.meowlib.etc;

public class Swappable<X> {

    private X first;
    private X second;

    public Swappable(X first, X second) {
        this();
        set(first, second);
    }

    public void set(X first, X second) {
        this.first = first;
        this.second = second;
    }

    public Swappable() { }

    public X swap() {
        X temp = first;
        first = second;
        second = temp;
        return first;
    }

    public X get() {
        return first;
    }
}
