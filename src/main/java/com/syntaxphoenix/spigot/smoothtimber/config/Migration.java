package com.syntaxphoenix.spigot.smoothtimber.config;

public abstract class Migration {

    protected static String ofEnum(final String enumName) {
        return enumName.toLowerCase().replace('_', '.').replace("222", "-");
    }

    protected static String enumPath(final String path, final String enumName) {
        return path + ofEnum(enumName);
    }

}
