package net.sourcewriters.smoothtimber.api.resource.key;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;

public final class ResourceKeyCache {

    private final ConcurrentHashMap<String, ResourceKey> keys = new ConcurrentHashMap<>();

    public ResourceKey get(SmoothTimberModule module, String value) {
        return get(module.getId(), value);
    }

    public ResourceKey get(String namespace, String value) {
        try {
            return keys.computeIfAbsent(namespace + ':' + value, (i) -> new ResourceKey(namespace, value));
        } catch (KeyFormatException kfe) {
            return null;
        }
    }

    public ResourceKey get(String value) {
        try {
            return keys.computeIfAbsent(value, ResourceKey::fromString);
        } catch (KeyFormatException kfe) {
            return null;
        }
    }

    public ResourceKey[] getNamespace(SmoothTimberModule module) {
        return getNamespace(module.getId());
    }

    public ResourceKey[] getNamespace(String namespace) {
        Iterator<String> iterator = keys.keys().asIterator();
        ArrayList<ResourceKey> output = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            ResourceKey current = keys.get(key);
            if (current == null || !current.getNamespace().equals(namespace)) {
                continue;
            }
            output.add(current);
        }
        return output.toArray(ResourceKey[]::new);
    }

    public void clearNamespace(String namespace) {
        Iterator<String> iterator = keys.keys().asIterator();
        ArrayList<String> remove = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            ResourceKey current = keys.get(key);
            if (current == null || !current.getNamespace().equals(namespace)) {
                continue;
            }
            remove.add(key);
        }
        for (String key : remove) {
            keys.remove(key);
        }
        remove.clear();
    }

    public void clear() {
        keys.clear();
    }

}
