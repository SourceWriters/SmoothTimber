package com.syntaxphoenix.spigot.smoothtimber.utilities;

public abstract class Parser {

    public static int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignore) {
            return 0;
        }
    }

}
