package net.sourcewriters.smoothtimber.api.tree;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlatform;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public final class SimpleWoodType implements IWoodType {

    private final String name;
    private final ResourceKey key;

    private final boolean supported;
    private final List<ResourceKey> types;

    public SimpleWoodType(ISmoothTimberPlatform platform, String name, ResourceKey key, ResourceKey... types) {
        this.name = name;
        this.key = key;
        this.types = Arrays.asList(types).stream().filter(type -> type != null && platform.isValidMaterial(type)).distinct()
            .collect(Collectors.toUnmodifiableList());
        this.supported = !this.types.isEmpty() && platform.isValidMaterial(key);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public ResourceKey key() {
        return key;
    }

    @Override
    public boolean isSupported() {
        return supported;
    }

    @Override
    public boolean isType(ResourceKey key) {
        return types.contains(key);
    }

}
