package net.sourcewriters.smoothtimber.core.platform.event.manager;

import net.sourcewriters.smoothtimber.core.platform.event.PlatformEvent;

public interface IPlatformEventAdapter {

    /**
     * Calls a event asynchronously on the platform side
     * 
     * @param  event                 the event to be called
     * 
     * @throws UnknownEventException if the event is unknown to the platform
     */
    default void call(final PlatformEvent event) throws UnknownEventException {
        call(event, true);
    }

    /**
     * Calls a event on the platform side
     * 
     * @param  event                 the event to be called
     * @param  async                 if the event should be called synchronous or
     *                                   asynchronous to the main thread
     * 
     * @throws UnknownEventException if the event is unknown to the platform
     */
    void call(PlatformEvent event, boolean async) throws UnknownEventException;

}
