package net.sourcewriters.smoothtimber.api.tree;

import java.util.Objects;

import net.sourcewriters.smoothtimber.api.ISmoothTimberRegistry;
import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;
import net.sourcewriters.smoothtimber.api.util.world.PlatformBlockCache;

public abstract class TreeDetector {

    private final ResourceKey key;
    protected final ISmoothTimberRegistry registry;

    /**
     * Constructs a tree detector
     * 
     * @param module the owning module
     * @param name   the name of the detector
     */
    public TreeDetector(final SmoothTimberModule module, final String name) {
        this.key = Objects.requireNonNull(module.key(name));
        this.registry = module.getApi().getCore().getRegistry();
    }

    /**
     * Gets the id of the tree detector
     * 
     * @return the id key
     */
    public final ResourceKey getKey() {
        return key;
    }

    /**
     * Detects if there is a tree at the given location
     * 
     * @param  blockCache the blockCache for the world that the tree is in
     * @param  location   the location that the tree is at
     * 
     * @return            the tree or null
     */
    public abstract ITree detect(PlatformBlockCache blockCache, Vector3i location);

}
