package net.sourcewriters.smoothtimber.api.resource.key;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;

public final class ResourceKeyCache {

    private final ConcurrentHashMap<String, ResourceKey> keys = new ConcurrentHashMap<>();

    public ResourceKey get(final SmoothTimberModule module, final String value) {
        return get(module.getId(), value);
    }

    public ResourceKey get(final String namespace, final String value) {
        try {
            return keys.computeIfAbsent(namespace + ':' + value, i -> new ResourceKey(namespace, value));
        } catch (final KeyFormatException kfe) {
            return null;
        }
    }

    public ResourceKey get(final String value) {
        try {
            return keys.computeIfAbsent(value, ResourceKey::fromString);
        } catch (final KeyFormatException kfe) {
            return null;
        }
    }

    public ResourceKey[] getNamespace(final SmoothTimberModule module) {
        return getNamespace(module.getId());
    }

    public ResourceKey[] getNamespace(final String namespace) {
        final Iterator<String> iterator = keys.keys().asIterator();
        final ArrayList<ResourceKey> output = new ArrayList<>();
        while (iterator.hasNext()) {
            final String key = iterator.next();
            final ResourceKey current = keys.get(key);
            if (current == null || !current.getNamespace().equals(namespace)) {
                continue;
            }
            output.add(current);
        }
        return output.toArray(ResourceKey[]::new);
    }

    public void clearNamespace(final String namespace) {
        final Iterator<String> iterator = keys.keys().asIterator();
        final ArrayList<String> remove = new ArrayList<>();
        while (iterator.hasNext()) {
            final String key = iterator.next();
            final ResourceKey current = keys.get(key);
            if (current == null || !current.getNamespace().equals(namespace)) {
                continue;
            }
            remove.add(key);
        }
        for (final String key : remove) {
            keys.remove(key);
        }
        remove.clear();
    }

    public void clear() {
        keys.clear();
    }

}
