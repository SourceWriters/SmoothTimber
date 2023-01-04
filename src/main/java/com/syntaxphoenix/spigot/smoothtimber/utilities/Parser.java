package com.syntaxphoenix.spigot.smoothtimber.utilities;

public abstract class Parser {

    public static int parseInt(final String value) {
        try {
            return Integer.parseInt(value);
        } catch (final NumberFormatException ignore) {
            return 0;
        }
    }

    public static long parseLong(final String value) {
        try {
            return Long.parseLong(value);
        } catch (final NumberFormatException ignore) {
            return 0L;
        }
    }

}
