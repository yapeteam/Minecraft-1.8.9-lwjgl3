package pisi.unitedmeows.meowlib.etc;


import java.io.Serializable;

public class MLibSetting<X extends Serializable> {

    private MLibSettings label;
    private X value;

    public MLibSetting(MLibSettings label, X value) {
        this.label = label;
        this.value = value;
    }

    public X getValue() {
        return value;
    }

    public MLibSettings getLabel() {
        return label;
    }
}
