package com.syntaxphoenix.spigot.smoothtimber.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class Lists {

    @SuppressWarnings("unchecked")
    public static <E> List<E> asList(final E... c) {
        final List<E> list = new ArrayList<>();
        for (int v = 0; v < c.length; v++) {
            list.add(c[v]);
        }
        return list;
    }

    public static <E> List<E> asList(final Collection<E> c) {
        final List<E> list = new ArrayList<>();
        final Iterator<E> it = c.iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        return list;
    }

    public static String getEIC(final String s, final List<String> l) {
        if (l.isEmpty()) {
            return "";
        }
        for (final String st : l) {
            if (st.equalsIgnoreCase(s)) {
                return st;
            }
        }
        return "";
    }

    public static boolean containsEIC(final String s, final List<String> l) {
        if (l.isEmpty()) {
            return false;
        }
        for (final String st : l) {
            if (st.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    public static String[] toStringArray(final Collection<String[]> coll) {
        final List<String> out = new ArrayList<>();
        coll.forEach(t -> {
            for (int v = 0; v < t.length; v++) {
                out.add(t[v]);
            }
        });
        return out.toArray(new String[0]);
    }

    public static <E> List<List<E>> partition(final List<E> list, final int num) {
        final List<List<E>> newList = new ArrayList<>();
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
