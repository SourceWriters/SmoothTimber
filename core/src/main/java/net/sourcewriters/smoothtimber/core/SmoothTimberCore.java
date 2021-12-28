package net.sourcewriters.smoothtimber.core;

import net.sourcewriters.smoothtimber.api.SmoothTimberApi;
import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;

public final class SmoothTimberCore extends SmoothTimberModule {

    private final SmoothTimberApi api;

    public SmoothTimberCore(final SmoothTimberApi api) {
        this.api = api;
    }

    public SmoothTimberApi getApi() {
        return api;
    }

}
