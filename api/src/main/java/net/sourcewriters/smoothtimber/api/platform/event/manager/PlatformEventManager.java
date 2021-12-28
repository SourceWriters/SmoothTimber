package net.sourcewriters.smoothtimber.api.platform.event.manager;

import java.util.concurrent.Future;

import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberExecutor;
import net.sourcewriters.smoothtimber.api.platform.event.PlatformEvent;

public final class PlatformEventManager {

    private final IPlatformEventAdapter adapter;
    private final ISmoothTimberExecutor executor;
    private final PlatformEventHandler handler = new PlatformEventHandler();

    public PlatformEventManager(final IPlatformEventAdapter adapter, final ISmoothTimberExecutor executor) {
        this.adapter = adapter;
        this.executor = executor;
    }

    //
    // Platform

    /**
     * @return the platform specific event adapter
     */
    public IPlatformEventAdapter getAdapter() {
        return adapter;
    }

    /**
     * Calls a event asynchronously on the platform side
     * 
     * @param  event                 the event to be called
     * 
     * @throws UnknownEventException if the event is unknown to the platform
     * 
     * @see                          net.sourcewriters.smoothtimber.api.platform.event.manager.IPlatformEventAdapter#call(PlatformEvent)
     */
    public void callPlatform(final PlatformEvent event) throws UnknownEventException {
        adapter.call(event);
    }

    /**
     * Calls a event on the platform side
     * 
     * @param  event                 the event to be called
     * @param  async                 if the event should be called synchronous or
     *                                   asynchronous to the main thread
     * 
     * @throws UnknownEventException if the event is unknown to the platform
     * 
     * @see                          net.sourcewriters.smoothtimber.api.platform.event.manager.IPlatformEventAdapter#call(PlatformEvent,
     *                                   boolean)
     */
    public void callPlatform(final PlatformEvent event, final boolean async) throws UnknownEventException {
        adapter.call(event, async);
    }

    //
    // Global

    /**
     * Calls a event asynchronously on the internal listeners
     * 
     * @param  event the event to be called
     * 
     * @return       the task future
     */
    public Future<?> call(final PlatformEvent event) {
        return call(event, true);
    }

    /**
     * Calls a event on the internal listeners
     * 
     * @param  event the event to be called
     * @param  async if the event should be called synchronous or asynchronous to
     *                   the main thread
     * 
     * @return       the task future
     */
    public Future<?> call(final PlatformEvent event, final boolean async) {
        return async ? executor.runAsync(() -> handler.call(event)) : executor.runSync(() -> handler.call(event));
    }

}
