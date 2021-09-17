package net.sourcewriters.smoothtimber.core.platform;

import net.sourcewriters.smoothtimber.core.util.math.Vector3i;

public interface IPlatformFallingBlock extends IPlatformEntity {

    /**
     * Drops the item corresponding to the block type of this entity at the
     * specified position with the specified amount
     * 
     * @param world  the world in which the items should be dropped in
     * @param vector the position at which the items will be dropped at
     * @param amount the amount of items that should be dropped
     */
    void dropItemAt(IPlatformWorld world, Vector3i vector, int amount);

}
