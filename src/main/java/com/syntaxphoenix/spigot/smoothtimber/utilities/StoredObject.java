package com.syntaxphoenix.spigot.smoothtimber.utilities;

public class StoredObject<E> {

    private E object;

    public StoredObject() {}

    public StoredObject(final E object) {
        this.object = object;
    }

    public void setObject(final E object) {
        this.object = object;
    }

    public E getObject() {
        return object;
    }

}
