package net.sourcewriters.smoothtimber.core;

import net.sourcewriters.smoothtimber.core.platform.ISmoothTimberPlatform;
import net.sourcewriters.smoothtimber.core.platform.event.manager.PlatformEventManager;

public final class SmoothTimberCore {

    private final ISmoothTimberPlatform platform;
    private final PlatformEventManager eventManager;

    /**
     * Constructs the core of SmoothTimber
     * 
     * @param platform the platform specific adapter
     */
    public SmoothTimberCore(final ISmoothTimberPlatform platform) {
        this.platform = platform;
        this.eventManager = new PlatformEventManager(platform.getEventAdapter());
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
