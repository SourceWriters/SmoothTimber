package net.sourcewriters.smoothtimber.core.platform;

import net.sourcewriters.smoothtimber.core.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.core.util.math.Vector3d;

public interface IPlatformEntity {

    /**
     * Get the namespaced key of the entity
     * 
     * @return the namespaced key
     */
    ResourceKey getResource();

    /**
     * Get the position of the entity
     * 
     * @return the position
     */
    Vector3d getPosition();

}
