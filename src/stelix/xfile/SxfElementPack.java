package stelix.xfile;

public class SxfElementPack<X, O>  {

    private final X element;
    private final O owner;
    private String comment;

    public SxfElementPack(X _element, O _owner) {
        element = _element;
        owner = _owner;
    }

    public SxfElementPack<X, O> comment(String _comment) {
        comment = _comment;
        return this;
    }

    public String comment() {
        return comment;
    }

    public X down() {
        return element;
    }

    public O build() {
        return owner;
    }
}
