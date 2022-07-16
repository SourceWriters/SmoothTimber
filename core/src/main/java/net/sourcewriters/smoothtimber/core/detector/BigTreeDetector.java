package net.sourcewriters.smoothtimber.core.detector;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.tree.ITree;
import net.sourcewriters.smoothtimber.api.tree.TreeDetector;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;
import net.sourcewriters.smoothtimber.api.util.world.PlatformBlockCache;

public final class BigTreeDetector extends TreeDetector {

    public BigTreeDetector(SmoothTimberModule module) {
        super(module, "big_tree");
    }

    @Override
    public ITree detect(PlatformBlockCache blockCache, Vector3i location) {
        return null;
    }

}
