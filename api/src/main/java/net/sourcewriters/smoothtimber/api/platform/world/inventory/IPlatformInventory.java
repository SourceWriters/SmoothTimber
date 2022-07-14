package net.sourcewriters.smoothtimber.api.platform.world.inventory;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.util.math.TableMath;

public interface IPlatformInventory {

    /**
     * Gets the handle of this inventory
     * 
     * @return the handle
     */
    Object getHandle();

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
     * @param row    the row of the target inventory slot
     * @param column the column of the target inventory slot
     * @param item   the item to be set
     */
    default void set(int row, int column, IPlatformItem item) {
        set(TableMath.getId(row, column), item);
    }

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
    default IPlatformItem get(int row, int column) {
        return get(TableMath.getId(row, column));
    }

    /**
     * Checks if the inventory name, size and type is modifiable
     * 
     * @return if name, size and type is modifiable or not
     */
    default boolean isModifiable() {
        return false;
    }

    /**
     * Gets the name of the inventory
     * 
     * @return the inventory name or null
     */
    default String getName() {
        return null;
    }

    /**
     * Sets the name of the inventory
     * 
     * @param  name the name to be set
     * 
     * @return      if the name changed or not
     */
    default boolean setName(String name) {
        return false;
    }

    /**
     * Gets the amount of slots that are in the inventory
     * 
     * @return the amount of inventory slots
     */
    int getSize();

    /**
     * Sets the amount of slots that are in the inventory
     * 
     * @param  size the amount of inventory slots
     * 
     * @return      if the size changed or not
     */
    default boolean setSize(int size) {
        return false;
    }

    /**
     * Gets the type of the inventory
     * 
     * @return the inventory type
     */
    ResourceKey getType();

    /**
     * Sets the type of the inventory
     * 
     * @param  key the inventory type to be set
     * 
     * @return     if the type changed or not
     */
    default boolean setType(ResourceKey key) {
        return false;
    }

}
