package net.sourcewriters.smoothtimber.core.platform.event.manager;

import net.sourcewriters.smoothtimber.core.platform.event.PlatformEvent;

public final class PlatformEventManager {

    private final IPlatformEventAdapter adapter;

    public PlatformEventManager(final IPlatformEventAdapter adapter) {
        this.adapter = adapter;
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
     * @see                          net.sourcewriters.smoothtimber.core.platform.event.manager.IPlatformEventAdapter#call(PlatformEvent)
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
     * @see                          net.sourcewriters.smoothtimber.core.platform.event.manager.IPlatformEventAdapter#call(PlatformEvent,
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
     * @param event the event to be called
     * @param async if the event should be called synchronous or asynchronous to the
     *                  main thread
     */
    public void call(final PlatformEvent event) {
        call(event, true);
    }

    /**
     * Calls a event on the internal listeners
     * 
     * @param event the event to be called
     * @param async if the event should be called synchronous or asynchronous to the
     *                  main thread
     */
    public void call(final PlatformEvent event, final boolean async) {

    }

}
