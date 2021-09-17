package net.sourcewriters.smoothtimber.core.platform;

import net.sourcewriters.smoothtimber.core.util.math.Vector3i;

public interface IPlatformFallingBlock extends IPlatformEntity {

    /**
     * Drops the item at corresponding to the block type of this entity the specific
     * position with the specific amount
     * 
     * @param world
     * @param vector
     * @param amount
     */
    void dropItemAt(IPlatformWorld world, Vector3i vector, int amount);

}
