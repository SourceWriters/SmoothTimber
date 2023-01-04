package com.syntaxphoenix.spigot.smoothtimber.utilities;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class Tracker {

    public static Optional<Class<?>> getClassFromStack(final int offset) {
        final StackTraceElement element = Thread.currentThread().getStackTrace()[2 + offset];
        return element == null ? Optional.empty() : Reflector.getOptionalClass(element.getClassName());
    }

    public static Optional<Class<?>> getCallerClass() {
        return getClassFromStack(1);
    }

    public static Optional<Plugin> getCallerPlugin() {
        final StackTraceElement[] elements = Arrays.subArray(StackTraceElement[]::new, Thread.currentThread().getStackTrace(), 2);
        for (final StackTraceElement element : elements) {
            final Optional<Plugin> plugin = getPlugin(Reflector.getOptionalClass(element.getClassName()));
            if (plugin.isPresent()) {
                return plugin;
            }
        }
        return Optional.empty();
    }

    public static Optional<Plugin> getPlugin(final Optional<Class<?>> option) {
        if (!option.isPresent()) {
            return Optional.empty();
        }
        final Class<?> clazz = option.get();
        return java.util.Arrays.stream(Bukkit.getPluginManager().getPlugins())
            .filter(plugin -> plugin.getClass().getClassLoader() == clazz.getClassLoader()).findFirst();
    }

}
