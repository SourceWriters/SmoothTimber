package net.sourcewriters.smoothtimber.core;

import net.sourcewriters.smoothtimber.core.platform.ISmoothTimberPlatform;
import net.sourcewriters.smoothtimber.core.platform.event.manager.PlatformEventManager;

public final class SmoothTimberCore {

    private final ISmoothTimberPlatform platform;
    private final PlatformEventManager eventManager;

    public SmoothTimberCore(final ISmoothTimberPlatform platform) {
        this.platform = platform;
        this.eventManager = new PlatformEventManager(platform.getEventAdapter());
    }

    public ISmoothTimberPlatform getPlatform() {
        return platform;
    }

    public PlatformEventManager getEventManager() {
        return eventManager;
    }

}
