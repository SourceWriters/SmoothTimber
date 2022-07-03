package net.sourcewriters.smoothtimber.spigot.util;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class DoneFuture<E> implements Future<E> {

    private static final DoneFuture<?> FUTURE = new DoneFuture<>(null);

    @SuppressWarnings("unchecked")
    public static <E> DoneFuture<E> empty() {
        return (DoneFuture<E>) FUTURE;
    }

    public static <E> DoneFuture<E> of(E value) {
        if (value == null) {
            return empty();
        }
        return new DoneFuture<>(value);
    }

    private final E value;

    private DoneFuture(E value) {
        this.value = value;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return true;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public E get() {
        return value;
    }

    @Override
    public E get(long timeout, TimeUnit unit) {
        return value;
    }

}
