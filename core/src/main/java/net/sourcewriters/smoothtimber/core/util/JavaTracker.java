package net.sourcewriters.smoothtimber.core.util;

import java.util.Optional;

public final class JavaTracker {

    private JavaTracker() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static StackTraceElement[] getStack() {
        return new Throwable().getStackTrace();
    }

    public static Optional<Class<?>> getClassFromStack(final int offset) {
        final StackTraceElement element = getStack()[3 + offset];
        if (element == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(JavaAccess.findClass(element.getClassName()));
    }

    public static Optional<Class<?>> getCallerClass() {
        return getClassFromStack(1);
    }

}