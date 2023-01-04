package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.adapter.handle;

import java.lang.invoke.MethodHandle;
import java.util.Objects;

public final class LookHandle {

    private final MethodHandle handle;

    public LookHandle(final MethodHandle handle) {
        this.handle = Objects.requireNonNull(handle, "MethodHandle can't be null!");
    }

    public MethodHandle getHandle() {
        return handle;
    }

    public Object invoke(final Object... arguments) {
        try {
            return handle.invoke(arguments);
        } catch (final Throwable e) {
            throw new IllegalStateException("Unable to invoke '" + handle.type().toString() + "'!");
        }
    }

    public Object invokeExact(final Object... arguments) {
        try {
            return handle.invokeExact(arguments);
        } catch (final Throwable e) {
            throw new IllegalStateException("Unable to invoke '" + handle.type().toString() + "'!");
        }
    }

    public Object invokeWithArguments(final Object... arguments) {
        try {
            return handle.invokeWithArguments(arguments);
        } catch (final Throwable e) {
            throw new IllegalStateException("Unable to invoke '" + handle.type().toString() + "'!");
        }
    }

}
