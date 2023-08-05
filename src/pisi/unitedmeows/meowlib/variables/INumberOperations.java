package pisi.unitedmeows.meowlib.variables;

public interface INumberOperations<X> {

    boolean bigger(X otherVal);
    boolean smaller(X otherVal);
    boolean same(X otherVal);
    void plus(X otherVal);
    void minus(X otherVal);

}
