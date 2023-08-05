package pisi.unitedmeows.meowlib.lists;


import java.util.ArrayList;
import java.util.List;

public class MeowList<X> {


    private List<Tail<X>> tails;
    private int size;

    public MeowList() {
        tails = new ArrayList<>();
    }

    public void push(X x) {
        if (size + 1 >= tails.size() * 5) {
            tails.add(new Tail<X>());
        }
        tails.get(tails.size() -1).push(x);
        size++;
    }

    public void remove(int index) {
        // credits to aris
        tails.get(((index - (index % 5)) / 5) + index % 5).remove(index);
        size--;
    }

    public X get (int index) {
        return tails.get(((index - (index % 5)) / 5)).get(index % 5);
    }


}
