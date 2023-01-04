package net.sourcewriters.smoothtimber.api.tree;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlatform;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public final class SimpleBlockType implements IBlockType {

    private final ResourceKey id;
    private final ResourceKey key;

    private final boolean wood;
    private final boolean supported;
    private final List<ResourceKey> types;

    public SimpleBlockType(ISmoothTimberPlatform platform, boolean wood, ResourceKey id, ResourceKey key, ResourceKey... types) {
        this.id = id;
        this.key = key;
        this.types = Arrays.asList(types).stream().filter(type -> type != null && platform.isValidMaterial(type)).distinct()
            .collect(Collectors.toUnmodifiableList());
        this.supported = !this.types.isEmpty() && platform.isValidMaterial(key);
        this.wood = wood;
    }

    @Override
    public ResourceKey id() {
        return id;
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
    public boolean isWood() {
        return wood;
    }

    @Override
    public boolean isType(ResourceKey key) {
        return types.contains(key);
    }

}
