package pisi.unitedmeows.meowlib.etc.memorable;

/* This list is using more ram to use codes faster (index of an element etc.) */

public class MemorableList<X> {
    private MemorableTail<X> currentTail;

    public MemorableList() {
        currentTail = new MemorableTail<>();
    }
    public MemorableList(int tailLength) {
        currentTail = new MemorableTail<>(tailLength);
    }

    public void push(X element) {
        currentTail.add(element);
    }
    public void kick(X element) {
        currentTail.remove(element);
    }
    public void kick(int index) {
        currentTail.removeAt(index);
    }

}
