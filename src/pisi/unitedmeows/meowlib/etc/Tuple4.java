package pisi.unitedmeows.meowlib.etc;

public class Tuple4<F, S, T, L> {

    private F first;
    private S second;
    private T third;
    private L last;


    public Tuple4(F first, S second, T third, L last) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.last = last;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public T getThird() {
        return third;
    }

    public L getLast() {
        return last;
    }
}
