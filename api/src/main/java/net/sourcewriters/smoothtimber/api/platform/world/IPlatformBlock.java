package net.sourcewriters.smoothtimber.api.platform.world;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

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
