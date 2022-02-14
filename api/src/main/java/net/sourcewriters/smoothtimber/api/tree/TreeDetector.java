package net.sourcewriters.smoothtimber.api.tree;

import java.util.Objects;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

public abstract class TreeDetector {

    private final ResourceKey key;

    public TreeDetector(SmoothTimberModule module, String name) {
        this.key = Objects.requireNonNull(module.key(name));
    }

    public final ResourceKey getKey() {
        return key;
    }

    public abstract ITree detect(IPlatformWorld world, Vector3i location);

}
