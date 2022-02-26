package net.sourcewriters.smoothtimber.api.tree;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.util.math.Cube;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

public interface ITree {

    /**
     * Get the blocks relative to the position with a maximum amount of blocks.
     * 
     * @param  relative the relative position of the block inside of the tree
     * @param  limit    the maximum of blocks being returned (optional)
     * 
     * @return          the blocks
     */
    IPlatformBlock[] getBlocks(Vector3i relative, int limit);

    /**
     * Get the world in that the tree is standing in
     * 
     * @return the world
     */
    IPlatformWorld getWorld();

    /**
     * Get the origin point of the tree
     * 
     * @return the origin
     */
    default Vector3i getOrigin() {
        return getShape().getOrigin();
    }

    /**
     * Get the cube box around the tree
     * 
     * @return the cube box
     */
    Cube getShape();

}
