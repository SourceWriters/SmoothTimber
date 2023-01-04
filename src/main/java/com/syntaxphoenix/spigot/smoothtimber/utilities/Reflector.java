/**
 * 
 * @author StevenLPHD
 * 
 */
package com.syntaxphoenix.spigot.smoothtimber.utilities;

import java.util.HashMap;
import java.util.Optional;

import org.bukkit.Bukkit;

public class Reflector {
    private static String serverVersion;

    public static String getServerVersion() {
        if (serverVersion == null) {
            serverVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        }
        return serverVersion;
    }

    private static final HashMap<String, Class<?>> cache = new HashMap<>();

    public static Class<?> getNMSClass(final String nmsClassString) {
        return getClass("net.minecraft.server." + getServerVersion() + "." + nmsClassString);
    }

    public static Class<?> getCBClass(final String cbClassString) {
        return getClass("org.bukkit.craftbukkit." + getServerVersion() + "." + cbClassString);
    }

    public static Class<?> getBL1Class(final String blClassString) {
        return getClass("com.syntaxphoenix.blocky." + blClassString);
    }

    public static Class<?> getBL2Class(final String blClassString) {
        return getClass("com.syntaxphoenix.blockylog." + blClassString);
    }

    public static Optional<Class<?>> getOptionalClass(final String classPath) {
        return Optional.ofNullable(getClass(classPath));
    }

    public static Class<?> getClass(final String classPath) {
        if (cache.containsKey(classPath)) {
            return cache.get(classPath);
        }
        try {
            final Class<?> clz = Class.forName(classPath);
            cache.put(classPath, clz);
            return clz;
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
