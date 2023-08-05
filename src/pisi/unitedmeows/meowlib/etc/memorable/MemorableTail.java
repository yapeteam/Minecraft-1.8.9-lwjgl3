package pisi.unitedmeows.meowlib.etc.memorable;

public class MemorableTail<X> {

    private MemorableTail<X> down;

    private MemorableElement<X>[] elements;
    private int length;
    private MemorableElement<X> lastElement;

    public MemorableTail() {
        elements = (MemorableElement<X>[]) new Object[length = 5];
    }
    public MemorableTail(int length) {
        elements = (MemorableElement<X>[]) new Object[this.length = length];
    }

    public MemorableElement<X>[] getElements() {
        return elements;
    }

    public int getLength() {
        return length;
    }

    public MemorableTail<X> getDown() {
        return down;
    }

    public void add(X element) {
        if (elements.length > length) {
            /* Throw an exception */
            //TODO: Exception
        }
        boolean needNewChild = lastElement.index + 1 >= length;
        MemorableTail<X> tail = lastElement.parentTail;
        MemorableElement<X> last = lastElement;
        if (needNewChild) {
            tail.down = new MemorableTail<>(length);
            tail.down.elements[0] = (MemorableElement<X>)element;
            tail.down.elements[0].element = element;
            tail.down.elements[0].index = 0;
            tail.down.elements[0].parentTail = tail.down;
        } else {
            tail.elements[last.index+1] = (MemorableElement<X>)element;
            tail.elements[last.index+1].element = element;
            tail.elements[last.index+1].index = last.index+1;
            tail.elements[last.index+1].parentTail = tail;
        }
    }
    public void remove(X element) {
        removeAt(((MemorableElement<X>)element).index);
    }
    public void removeAt(int index) {
        elements[index].element = null;
        int i = index + 1;
        for (; i < elements.length && elements[i] != null; i++) {
            elements[i - 1].element = elements[i].element;
            elements[i - 1].index = i-1;
        }
        MemorableTail<X> children = down;
        boolean lastChild = children != null;
        if (!lastChild) {
            lastElement = elements[i - 1];
        }
        i = 1;
        while (children != null && elements[elements.length - 1].element == null && children.elements.length > 0) {
            elements[elements.length - 1].element = children.elements[0].element;
            elements[elements.length - 1].index = elements.length - 1;
            elements[elements.length - 1].parentTail = this;
            for (; i < children.elements.length && children.elements[i] != null; i++) {
                children.elements[i - 1].element = children.elements[i].element;
                children.elements[i - 1].index = i-1;
            }
            children = children.down;
        }
        if (lastChild) {
            lastElement = elements[i - 1];
        }
    }
}
