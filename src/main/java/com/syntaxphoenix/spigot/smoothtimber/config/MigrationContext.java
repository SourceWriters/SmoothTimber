package com.syntaxphoenix.spigot.smoothtimber.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class MigrationContext {

    private final Map<String, Object> values;

    public MigrationContext(final YamlConfiguration configuration) {
        this.values = mapRootSection(configuration);
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public <E> MigrationContext map(final String path, final Class<E> sample, final Function<E, Object> mapper) {
        if (values.containsKey(path)) {
            final E value = safeCast(sample, values.remove(path));
            if (value == null) {
                return this;
            }
            values.put(path, mapper.apply(value));
        }
        return this;
    }

    public MigrationContext remove(final String path) {
        values.remove(path);
        return this;
    }

    public MigrationContext move(final String path, final String newPath) {
        if (values.containsKey(path)) {
            values.put(newPath, values.remove(path));
        }
        return this;
    }

    public MigrationContext stack(final String stack, final String path) {
        if (values.containsKey(path)) {
            values.put(stack + '.' + path, values.remove(path));
        }
        return this;
    }

    private <E> E safeCast(final Class<E> sample, final Object value) {
        return sample.isInstance(value) ? sample.cast(value) : null;
    }

    /*
     * Mapping
     */

    public static final String KEY = "%s.%s";

    public static Map<String, Object> mapRootSection(final ConfigurationSection section) {
        final LinkedHashMap<String, Object> output = new LinkedHashMap<>();
        for (final String key : section.getKeys(false)) {
            final Object value = section.get(key);
            if (value instanceof ConfigurationSection) {
                mapSubSection(output, (ConfigurationSection) value);
                continue;
            }
            output.put(key, value);
        }
        return output;
    }

    public static void mapSubSection(final Map<String, Object> output, final ConfigurationSection section) {
        final String path = section.getCurrentPath();
        for (final String key : section.getKeys(false)) {
            final Object value = section.get(key);
            if (value instanceof ConfigurationSection) {
                mapSubSection(output, (ConfigurationSection) value);
                continue;
            }
            output.put(String.format(KEY, path, key), value);
        }
    }

}