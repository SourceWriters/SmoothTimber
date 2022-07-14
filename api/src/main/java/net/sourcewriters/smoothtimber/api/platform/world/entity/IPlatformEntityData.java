package net.sourcewriters.smoothtimber.api.platform.world.entity;

import net.sourcewriters.smoothtimber.api.platform.world.data.IPlatformData;

public interface IPlatformEntityData<E extends IPlatformEntity> {
    
    /**
     * Gets the owning entity
     * 
     * @return the entity
     */
    E getEntity();

    /**
     * Checks if persistent data is supported
     * 
     * @return if persistent data is supported or not
     */
    boolean isPersistentDataSupported();

    /**
     * Gets the persistent data of this entity
     * 
     * @return the persistent data
     */
    IPlatformData getPersistentData() throws UnsupportedOperationException;

    /**
     * Gets the meta data of this entity
     * 
     * @return the meta data
     */
    IPlatformData getMetaData();

}
