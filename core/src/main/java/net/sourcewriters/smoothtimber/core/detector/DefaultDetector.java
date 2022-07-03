package net.sourcewriters.smoothtimber.core.detector;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.tree.ITree;
import net.sourcewriters.smoothtimber.api.tree.TreeDetector;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

public final class DefaultDetector extends TreeDetector {

    public DefaultDetector(SmoothTimberModule module) {
        super(module, "default");
    }

    @Override
    public ITree detect(IPlatformWorld world, Vector3i location) {
        
        return null;
    }

}
