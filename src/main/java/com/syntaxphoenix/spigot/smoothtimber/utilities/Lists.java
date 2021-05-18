package com.syntaxphoenix.spigot.smoothtimber.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public abstract class Lists {

    @SuppressWarnings("unchecked")
    public static <E> List<E> asList(E... c) {
        List<E> list = new ArrayList<E>();
        for (int v = 0; v < c.length; v++) {
            list.add(c[v]);
        }
        return list;
    }

    public static <E> List<E> asList(Collection<E> c) {
        List<E> list = new ArrayList<E>();
        Iterator<E> it = c.iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        return list;
    }

    public static String getEIC(String s, List<String> l) {
        if (l.isEmpty()) {
            return "";
        }
        for (String st : l) {
            if (st.equalsIgnoreCase(s)) {
                return st;
            }
        }
        return "";
    }

    public static boolean containsEIC(String s, List<String> l) {
        if (l.isEmpty()) {
            return false;
        }
        for (String st : l) {
            if (st.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    public static String[] toStringArray(Collection<String[]> coll) {
        List<String> out = new ArrayList<>();
        coll.forEach(new Consumer<String[]>() {
            @Override
            public void accept(String[] t) {
                for (int v = 0; v < t.length; v++) {
                    out.add(t[v]);
                }
            }
        });
        return out.toArray(new String[0]);
    }

    public static <E> List<List<E>> partition(List<E> list, int num) {
        List<List<E>> newList = new ArrayList<>();
        List<E> nList = new ArrayList<>();
        for (int v = 0; v < list.size(); v++) {
            if (nList.size() == num) {
                newList.add(nList);
                nList = new ArrayList<>();
            }
            nList.add(list.get(v));
        }
        if (!nList.isEmpty()) {
            newList.add(nList);
        }
        return newList;
    }

}
