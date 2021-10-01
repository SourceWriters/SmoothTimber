package net.sourcewriters.smoothtimber.core.platform.world;

import net.sourcewriters.smoothtimber.core.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.core.util.math.Vector3i;

public interface IPlatformBlock {

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

}
