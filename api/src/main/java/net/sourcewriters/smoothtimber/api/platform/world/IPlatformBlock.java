package net.sourcewriters.smoothtimber.api.platform.world;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

public interface IPlatformBlock {

    /**
     * Gets the handle of this block
     * 
     * @return the handle
     */
    Object getHandle();

    /**
     * Sets the block type
     * 
     * @param type the type to be changed to
     */
    void setType(ResourceKey type);

    /**
     * Get the namespaced key of the block
     * 
     * @return the namespaced key
     */
    ResourceKey getType();

    /**
     * Get the position of the block
     * 
     * @return the position
     */
    Vector3i getPosition();

    /**
     * Get the world that the block is in
     * 
     * @return the world
     */
    IPlatformWorld getWorld();

}
