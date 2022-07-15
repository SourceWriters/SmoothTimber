package net.sourcewriters.smoothtimber.api.platform.world.inventory;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public interface IPlatformItem {

    /**
     * Gets the handle of this item
     * 
     * @return the handle
     */
    Object getHandle();

    /**
     * Creates a exact copy of this item
     * 
     * @return the copy item
     */
    IPlatformItem clone();

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
     * Gets the amount of the item
     * 
     * @return the item amount
     */
    int getAmount();

    /**
     * Sets the amount of the item
     * 
     * @param amount the target amount
     */
    void setAmount(int amount);

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
