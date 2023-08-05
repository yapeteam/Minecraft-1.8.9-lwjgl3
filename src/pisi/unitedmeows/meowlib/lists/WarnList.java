package pisi.unitedmeows.meowlib.lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

public abstract class WarnList<X> extends ArrayList<X> {

    public abstract void onChange(WarnList<X> list);

    @Override
    public boolean add(X x) {
        boolean result = super.add(x);
        onChange(this);
        return result;
    }

    @Override
    public void add(int index, X element) {
        super.add(index, element);
        onChange(this);
    }

    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);
        if (result) {
            onChange(this);
            return true;
        }
        return false;
    }

    @Override
    public X remove(int index) {
        X result = super.remove(index);
        onChange(this);
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends X> c) {
        boolean result = super.addAll(c);
        if (result) {
            onChange(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends X> c) {
        boolean result = super.addAll(index, c);
        if (result) {
            onChange(this);
            return true;
        }
        return false;
    }


    @Override
    public X set(int index, X element) {
        X result = super.set(index, element);
        onChange(this);
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = super.removeAll(c);
        if (result) {
            onChange(this);
            return true;
        }
        return false;
    }

    @Override
    public void sort(Comparator<? super X> c) {
        super.sort(c);
        onChange(this);
    }

    @Override
    public boolean removeIf(Predicate<? super X> filter) {
        boolean result = super.removeIf(filter);
        if (result) {
            onChange(this);
            return true;
        }
        return false;
    }

}
