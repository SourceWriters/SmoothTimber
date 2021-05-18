package com.syntaxphoenix.spigot.smoothtimber.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class ListQueue<E> implements Queue<E> {

    private final ArrayList<E> list = new ArrayList<>();

    public ArrayList<E> getList() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return list.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean add(E e) {
        if (list.contains(e)) {
            return false;
        }
        return list.add(e);
    }

    @Override
    public boolean offer(E e) {
        try {
            return add(e);
        } catch (IllegalStateException ignore) {
            return false;
        }
    }

    @Override
    public E remove() {
        return list.remove(0);
    }

    @Override
    public E poll() {
        try {
            return remove();
        } catch (IndexOutOfBoundsException ignore) {
            return null;
        }
    }

    @Override
    public E element() {
        return list.get(0);
    }

    @Override
    public E peek() {
        try {
            return element();
        } catch (IndexOutOfBoundsException ignore) {
            return null;
        }
    }

}
