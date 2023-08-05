package pisi.unitedmeows.meowlib.lists;

import pisi.unitedmeows.meowlib.etc.IFilter;

import java.util.ArrayList;
import java.util.List;

public class list {


    public static <X> ArrayList filter_list(List<X> _list, IFilter<X> filter) {
        _list.removeIf(x-> !filter.check(x));
        return (ArrayList) _list;
    }
}
