package com.syntaxphoenix.spigot.smoothtimber.config;

import java.util.function.Function;
import java.util.function.Supplier;

public enum ActionType {

    DISABLED("none", state -> false),
    ON_ACTION("on", state -> !state.get()),
    OFF_ACTION("off", Supplier::get);

    private final String value;
    private final Function<Supplier<Boolean>, Boolean> function;

    ActionType(final String value, final Function<Supplier<Boolean>, Boolean> function) {
        this.value = value;
        this.function = function;
    }

    public String value() {
        return value;
    }

    public boolean isDisabled() {
        return this == DISABLED;
    }

    public boolean test(final Supplier<Boolean> value) {
        return function.apply(value);
    }

    public static ActionType byName(final String name) {
        for (final ActionType type : values()) {
            if (type.value().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return ActionType.DISABLED;
    }

    public static ActionType check(final STConfig config, final String path, final ActionType value) {
        return byName(config.check(path, value.value()));
    }

}
