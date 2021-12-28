package net.sourcewriters.smoothtimber.api;

import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlatform;
import net.sourcewriters.smoothtimber.api.platform.event.manager.PlatformEventManager;

public final class SmoothTimberApi {

    private final ISmoothTimberPlatform platform;
    private final PlatformEventManager eventManager;

    /**
     * Constructs the core of SmoothTimber
     * 
     * @param platform the platform specific adapter
     */
    public SmoothTimberApi(final ISmoothTimberPlatform platform) {
        this.platform = platform;
        this.eventManager = new PlatformEventManager(platform.getEventAdapter(), platform.getExecutor());
    }

    /**
     * Gets the platform specific adapter
     * 
     * @return the platform adapter
     */
    public ISmoothTimberPlatform getPlatform() {
        return platform;
    }

    /**
     * Gets the internal event manager
     * 
     * @return the event manager
     */
    public PlatformEventManager getEventManager() {
        return eventManager;
    }

}
