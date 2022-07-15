package net.sourcewriters.smoothtimber.api.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class TaskFuture<E> implements Runnable, Future<E> {

    private final Callable<E> task;

    private E value;
    private Consumer<Throwable> exceptionHandler;

    private final AtomicBoolean executed = new AtomicBoolean(false);

    public TaskFuture(Runnable runnable) {
        this((Callable<E>) () -> {
            runnable.run();
            return null;
        });
    }

    public TaskFuture(Supplier<E> supplier) {
        this((Callable<E>) () -> supplier.get());
    }

    public TaskFuture(Callable<E> task) {
        this.task = task;
    }

    public void setExceptionHandler(Consumer<Throwable> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void run() {
        try {
            value = task.call();
        } catch (Throwable throwable) {
            if (exceptionHandler != null) {
                exceptionHandler.accept(throwable);
            }
        }
        executed.set(true);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return executed.get();
    }

    @Override
    public E get() throws InterruptedException, ExecutionException {
        while (executed.get()) {
            Thread.sleep(20);
        }
        return value;
    }

    @Override
    public E get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        long millis = unit.toMillis(timeout);
        while (executed.get()) {
            if (millis-- == 0) {
                throw new TimeoutException("Task wasn't done in time!");
            }
            Thread.sleep(1);
        }
        return value;
    }

}