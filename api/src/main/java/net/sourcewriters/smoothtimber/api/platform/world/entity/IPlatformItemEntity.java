package net.sourcewriters.smoothtimber.api.platform.world.entity;

import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;

public interface IPlatformItemEntity extends IPlatformEntity {

    /**
     * Gets the data of the item entity
     * 
     * @return the data
     */
    IPlatformEntityData<IPlatformItemEntity> getData();

    /**
     * Gets the pickup delay of the item entity
     * 
     * @return the pickup delay in ticks
     */
    int getPickupDelay();

    /**
     * Sets the pickup delay of the item entity
     * 
     * @param delay the pickup delay in ticks
     */
    void setPickupDelay(int delay);

    /**
     * Gets the item associated with this item entity
     * 
     * @return the item
     */
    IPlatformItem getItem();

    /**
     * Sets the item associated with this item entity
     * 
     * @param item the item to set
     */
    void setItem(IPlatformItem item);

}
