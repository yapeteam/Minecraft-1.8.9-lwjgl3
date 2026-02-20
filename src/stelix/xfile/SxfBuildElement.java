package stelix.xfile;

public abstract class SxfBuildElement {
    protected WriteStyle style = WriteStyle.NORMAL;

    public abstract void writeObject(int spaceCount, StringBuilder stringBuilder);

    private SxfBuildElement owner;
    public SxfBuildElement setStyle(WriteStyle _style) {
        style = _style;
        return this;
    }

    public WriteStyle style() {
        return style;
    }

    protected void setOwner(SxfBuildElement _owner) {
        owner = _owner;
        if (owner != null) {
            style = owner.style;
        }
    }

    protected <X extends SxfBuildElement> X build() {
        if (owner == null) {
            return (X) this;
        }
        return (X) owner;
    }

    public SxfStructBuilder buildStruct() {
        return build();
    }

    public SxfBlockBuilder buildBlock() {
        return build();
    }
}