package net.sourcewriters.smoothtimber.core.detector;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.tree.ITree;
import net.sourcewriters.smoothtimber.api.tree.TreeDetector;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;
import net.sourcewriters.smoothtimber.api.util.world.PlatformBlockCache;

public final class SmallTreeDetector extends TreeDetector {

    public SmallTreeDetector(SmoothTimberModule module) {
        super(module, "small_tree");
        
    }

    @Override
    public ITree detect(PlatformBlockCache blockCache, Vector3i location) {
        ResourceKey key = blockCache.getBlockType(location);
        
        
        
        return null;
    }

}
