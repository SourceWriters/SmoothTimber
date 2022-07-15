package net.sourcewriters.smoothtimber.api.platform;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface ISmoothTimberExecutor {

    /**
     * Execute a task asynchronously
     * 
     * @param  task the task to be executed
     * 
     * @return      the task future
     */
    Future<?> runAsync(Runnable task);

    /**
     * Execute a task asynchronously
     * 
     * @param  task the task to be executed
     * 
     * @return      the task future
     */
    <E> Future<E> supplyAsync(Callable<E> task);

    /**
     * Execute a task synchronously
     * 
     * @param  task the task to be executed
     * 
     * @return      the task future
     */
    Future<?> runSync(Runnable task);

    /**
     * Execute a task synchronously
     * 
     * @param  task the task to be executed
     * 
     * @return      the task future
     */
    <E> Future<E> supplySync(Callable<E> task);

    /**
     * Checks if the current thread is the main thread
     * 
     * @return if it is the main thread
     */
    boolean isSync();

}
