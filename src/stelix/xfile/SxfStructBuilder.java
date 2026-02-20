package stelix.xfile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SxfStructBuilder extends SxfBuildElement {

    protected SxfStructBuilder(SxfBuildElement _owner) {
        style = _owner == null ? WriteStyle.INLINE : _owner.style();
    }

    public static SxfStructBuilder create() {
        return new SxfStructBuilder(null);
    }

    public static SxfStructBuilder create(SxfBuildElement _owner) {
        return new SxfStructBuilder(_owner);
    }

    private final List<SxfElementPack<Object, SxfStructBuilder>> elements = new ArrayList<>();

    public SxfElementPack<Object, SxfStructBuilder> add(Object element) {
        SxfElementPack<Object, SxfStructBuilder> objectPack = new SxfElementPack<>(element, this);
        elements.add(objectPack);
        if (element instanceof SxfBuildElement) {
            ((SxfBuildElement)element).setOwner(this);
        }
        return objectPack;
    }

    public List<SxfElementPack<Object, SxfStructBuilder>> elements() {
        return elements;
    }

    @Override
    public void writeObject(int spaceCount, StringBuilder stringBuilder) {
        // [ ]
        stringBuilder.append("[");

        Iterator<SxfElementPack<Object, SxfStructBuilder>> elementIterator = elements.iterator();
        boolean hasNext;
        if (elementIterator.hasNext()) {
            do {
                SxfElementPack<Object, SxfStructBuilder> pack = elementIterator.next();
                hasNext = elementIterator.hasNext();
                Object element = pack.down();

                if (element instanceof SxfBuildElement) {
                    if (style == WriteStyle.NORMAL) {
                        if (pack.comment() != null && !pack.comment().isEmpty()) {
                            stringBuilder.append(System.lineSeparator());
                            Commons.putSpace(stringBuilder, spaceCount + 1);
                            stringBuilder.append("$;").append(pack.comment()).append(" ;$");
                        }
                        stringBuilder.append(System.lineSeparator());
                        Commons.putSpace(stringBuilder, spaceCount + 1);
                    } else {
                        stringBuilder.append(" ");
                    }
                    if (element instanceof SxfStructBuilder) {
                        ((SxfStructBuilder) element).writeObject(spaceCount + 1, stringBuilder);
                    } else if (element instanceof SxfBlockBuilder) {
                        ((SxfBlockBuilder) element).writeObject(spaceCount + 1, stringBuilder);
                    }

                } else {
                    if (style == WriteStyle.NORMAL) {
                        stringBuilder.append(System.lineSeparator());
                        Commons.putSpace(stringBuilder, spaceCount + 1);
                    } else {
                        stringBuilder.append(" ");
                    }
                    stringBuilder.append(Commons.writeVar(element));
                    if (style == WriteStyle.NORMAL) {
                        if (pack.comment() != null && !pack.comment().isEmpty()) {
                            stringBuilder.append(" $; ").append(pack.comment()).append(" ;$");
                        }
                    }
                }
                if (hasNext) {
                    if (style == WriteStyle.NORMAL) {
                        stringBuilder.append(", ");
                    } else {
                        stringBuilder.append(", ");
                    }
                }



            } while (hasNext);
        }
        if (style == WriteStyle.NORMAL) {
            stringBuilder.append(System.lineSeparator());
            Commons.putSpace(stringBuilder, spaceCount);
        } else {
            stringBuilder.append(" ");
        }

        stringBuilder.append("]");
    }

    @Override
    public SxfStructBuilder setStyle(WriteStyle _style) {
        return (SxfStructBuilder) super.setStyle(_style);
    }


}
