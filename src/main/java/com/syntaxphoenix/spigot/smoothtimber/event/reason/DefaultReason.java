package com.syntaxphoenix.spigot.smoothtimber.event.reason;

import com.syntaxphoenix.spigot.smoothtimber.config.Message;

public enum DefaultReason implements IChopReason {

    TOWNY,
    GRIEFPREVENTION,
    WORLDGUARD,
    RESIDENCE,
    LANDS,
    UNKNOWN;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getMessage() {
        return Message.valueOf("ABORT_REASON_" + name()).colored();
    }

}
