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
    public boolean contains(final Object o) {
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
    public <T> T[] toArray(final T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean remove(final Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        return list.addAll(c);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean add(final E e) {
        if (list.contains(e)) {
            return false;
        }
        return list.add(e);
    }

    @Override
    public boolean offer(final E e) {
        try {
            return add(e);
        } catch (final IllegalStateException ignore) {
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
        } catch (final IndexOutOfBoundsException ignore) {
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
        } catch (final IndexOutOfBoundsException ignore) {
            return null;
        }
    }

}
