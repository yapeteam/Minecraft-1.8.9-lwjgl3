package stelix.xfile;

public class Variable {
    private final String name;
    private final Object value;


    public Variable(String _name, Object _value) {
        name = _name;
        value = _value;
    }

    public String name() {
        return name;
    }

    public Object value() {
        return value;
    }
}
