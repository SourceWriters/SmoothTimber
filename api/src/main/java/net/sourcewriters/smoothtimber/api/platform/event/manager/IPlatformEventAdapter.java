package net.sourcewriters.smoothtimber.api.platform.event.manager;

import java.util.concurrent.Future;

import net.sourcewriters.smoothtimber.api.platform.event.PlatformEvent;

public interface IPlatformEventAdapter {

    /**
     * Calls a event asynchronously on the platform side
     * 
     * @param  event                              the event to be called
     * 
     * @return                                    the future of the event call
     * 
     * @throws UnknownEventException              if the event is unknown to the
     *                                                platform
     * @throws UnsupportedEventDirectionException if the event is can't be used in
     *                                                this direction
     */
    default Future<?> call(final PlatformEvent event) throws UnknownEventException, UnsupportedEventDirectionException {
        return call(event, true);
    }

    /**
     * Calls a event on the platform side
     * 
     * @param  event                              the event to be called
     * @param  async                              if the event should be called
     *                                                synchronous or asynchronous to
     *                                                the main thread
     * 
     * @return                                    the future of the event call
     * 
     * @throws UnknownEventException              if the event is unknown to the
     *                                                platform
     * @throws UnsupportedEventDirectionException if the event is can't be used in
     *                                                this direction
     */
    Future<?> call(PlatformEvent event, boolean async) throws UnknownEventException, UnsupportedEventDirectionException;

}
