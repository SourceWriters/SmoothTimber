package com.syntaxphoenix.spigot.smoothtimber.toggle;

import com.syntaxphoenix.spigot.smoothtimber.platform.Platform;

public class ToggleTimer implements Runnable {

    private final ToggleStorage storage;

    public ToggleTimer(final ToggleStorage storage) {
        this.storage = storage;
        Platform.getPlatform().asyncTaskTimer(this, 0, 0);
    }

    public final ToggleStorage getStorage() {
        return storage;
    }

    @Override
    public void run() {
        storage.update();
    }

}
