package net.sourcewriters.smoothtimber.api.tree;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.util.math.Cube;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

public interface ITree {

    IPlatformBlock[] getBlocks(Vector3i relative, int limit);

    IPlatformWorld getWorld();
    
    Cube getShape();

}
