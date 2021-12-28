package net.sourcewriters.smoothtimber.api.platform.world.inventory;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public interface IPlatformInventory {

    /**
     * Add an item to the inventory
     * 
     * @param item the item to be added
     */
    void add(IPlatformItem item);

    /**
     * Sets a specific slot to an item
     * 
     * @param slot the target inventory slot
     * @param item the item to be set
     */
    void set(int slot, IPlatformItem item);

    /**
     * Sets a specific slot to an item
     * 
     * @param column the column of the target inventory slot
     * @param row    the row of the target inventory slot
     * @param item   the item to be set
     */
    void set(int column, int row, IPlatformItem item);

    /**
     * Gets the item that is in a specific slot
     * 
     * @param  slot the target inventory slot
     * 
     * @return      the item that is in that slot or {@code null}
     */
    IPlatformItem get(int slot);

    /**
     * Gets the item that is in a specific slot
     * 
     * @param  column the column of the target inventory slot
     * @param  row    the row of the target inventory slot
     * 
     * @return        the item that is in that slot or {@code null}
     */
    IPlatformItem get(int column, int row);

    /**
     * Gets the name of the inventory
     * 
     * @return the inventory name
     */
    String getName();

    /**
     * Sets the name of the inventory
     * 
     * @param name the name to be set
     */
    void setName(String name);

    /**
     * Gets the amount of slots that are in the inventory
     * 
     * @return the amount of inventory slots
     */
    int getSize();

    /**
     * Sets the amount of slots that are in the inventory
     * 
     * @param size the amount of inventory slots
     */
    void setSize(int size);

    /**
     * Gets the type of the inventory
     * 
     * @return the inventory type
     */
    ResourceKey getType();

    /**
     * Sets the type of the inventory
     * 
     * @param key the inventory type to be set
     */
    boolean setType(ResourceKey key);

}
