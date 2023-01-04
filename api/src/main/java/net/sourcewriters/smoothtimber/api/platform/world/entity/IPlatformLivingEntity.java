package net.sourcewriters.smoothtimber.api.platform.world.entity;

import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformEquipment;

public interface IPlatformLivingEntity extends IPlatformEntity {

    /**
     * Gets the data of the entity
     * 
     * @return the data
     */
    IPlatformEntityData<? extends IPlatformLivingEntity> getData();

    /**
     * Gets the equipment of the entity
     * 
     * @return the entity equipment
     */
    IPlatformEquipment getEquipment();

    /**
     * Checks if the entity is visible or not
     * 
     * @return if the entity is not visible
     */
    boolean isInvisible();

    /**
     * Sets if the entity can be seen or not
     * 
     * @param invisible the state if the entity can not be seen
     */
    void setInvisible(boolean invisible);

    /**
     * Checks if the entity is able to collide with other entities
     * 
     * @return if the entity is collidable
     */
    boolean isCollidable();

    /**
     * Sets if the entity can collide with other entities
     * 
     * @param collidable the state if the entity can collide
     */
    void setCollidable(boolean collidable);

}
