package net.sourcewriters.smoothtimber.api.platform.world.inventory;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public interface IPlatformItem {

    /**
     * Gets the type of the item
     * 
     * @return the item type
     */
    ResourceKey getType();

    /**
     * Checks if the item has the specified type
     * 
     * @param  key the item type to be compared to
     * 
     * @return     if the item has the type
     */
    boolean hasType(ResourceKey key);

    /**
     * Sets the type of the item
     * 
     * @param  key the item type to be set to
     * 
     * @return     if the item has changed or not
     */
    boolean setType(ResourceKey key);

    /**
     * Gets the maximal stack amount of the item type
     * 
     * @return the maximal stack amount
     */
    int getMaxAmount();

    /**
     * Gets the maximal durability that the item has
     * 
     * @return the maximal durability
     */
    int getMaxDurability();

    /**
     * Gets the metadata of the item
     * 
     * @return the item specific meta
     */
    IPlatformItemMeta meta();

}
