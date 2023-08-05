package pisi.unitedmeows.meowlib.lists;


import java.util.Arrays;

/* dont use it */
@Deprecated
public class FlexibleArray<X> {

    private X[] array;
    private int count;
    private int resizeCap;

    public FlexibleArray(int capacity) {
        this(capacity, 1);
    }
    public FlexibleArray(int capacity, int resizeCap) {
        array = (X[]) newArray(capacity);
        this.resizeCap = resizeCap;
    }

    public FlexibleArray() {
        this(10);
    }

    public void resize(int newCount) {
        X[] old = array;
        array = (X[]) newArray(newCount);
        for (int i = 0; i < newCount && i < old.length; i++) {
            array[i] = (X) old[i];
        }
    }


    public void add(X element) {
        if (++count >= array.length) {
            resize(array.length + resizeCap);
        }
        array[count] = (X) element;
    }


    public boolean remove(X element) {
        int index = 0;
        boolean found = false;
        for (X x : array) {
            if (x == element) {
                found = true;
                break;
            }
            index++;
        }

        if (found && index + 1 != array.length) {
            for (int i = index + 1; i < array.length; i++) {
                array[index - 1] = (X) array[i];
            }
            count--;
        }

        if (count + resizeCap <= array.length) {
            resize(count + 1);
        }


        return found;
    }

    public boolean remove(int index) {
        if (index >= array.length || index < 0) {
            return false;
        }

        if (index + 1 != array.length) {
            for (int i = index + 1; i < array.length; i++) {
                array[index - 1] = (X) array[i];
            }
            count--;
        }

        if (count + resizeCap <= array.length) {
            resize(count + 1);
        }

        return true;
    }

    public X[] array() {
        return (X[]) array;
    }


    private X[] newArray(int length, X... array)
    {
        return Arrays.copyOf(array, length);
    }

    public X get(int index) {
        return array[index];
    }
}
