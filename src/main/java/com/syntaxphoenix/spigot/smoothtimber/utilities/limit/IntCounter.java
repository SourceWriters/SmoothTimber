package com.syntaxphoenix.spigot.smoothtimber.utilities.limit;

public final class IntCounter {

    private int value = 0;

    public synchronized IntCounter increment() {
        ++value;
        return this;
    }

    public synchronized IntCounter decrement() {
        --value;
        return this;
    }

    public synchronized IntCounter reset() {
        value = 0;
        return this;
    }

    public synchronized int get() {
        return value;
    }

}
