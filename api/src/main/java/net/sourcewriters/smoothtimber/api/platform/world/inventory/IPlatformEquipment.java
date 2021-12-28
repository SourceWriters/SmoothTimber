package net.sourcewriters.smoothtimber.api.platform.world.inventory;

public interface IPlatformEquipment {

    /**
     * Get the item that the entity has in its main hand
     * 
     * @return the item in the main hand
     */
    IPlatformItem getMainHand();

    /**
     * Sets the item that the entity has in its main hand
     * 
     * @param item the item to be set
     */
    void setMainHand(IPlatformItem item);

    /**
     * Get the item that the entity has in its off hand
     * 
     * @return the item in the off hand
     */
    IPlatformItem getOffHand();

    /**
     * Sets the item that the entity has in its off hand
     * 
     * @param item the item to be set
     */
    void setOffHand(IPlatformItem item);

    /**
     * Get the item that the entity has it has on its head
     * 
     * @return the item on the head
     */
    IPlatformItem getHelmet();

    /**
     * Sets the item that the entity has it has on its head
     * 
     * @param item the item to be set
     */
    void setHelmet(IPlatformItem item);

    /**
     * Get the item that the entity has it has on its chest
     * 
     * @return the item on the chest
     */
    IPlatformItem getChestplate();

    /**
     * Sets the item that the entity has it has on its chest
     * 
     * @param item the item to be set
     */
    void setChestplate(IPlatformItem item);

    /**
     * Get the item that the entity has it has on its legs
     * 
     * @return the item on the legs
     */
    IPlatformItem getLeggings();

    /**
     * Sets the item that the entity has it has on its legs
     * 
     * @param item the item to be set
     */
    void setLeggings(IPlatformItem item);

    /**
     * Get the item that the entity has it has on its foot
     * 
     * @return the item on the foot
     */
    IPlatformItem getBoots();

    /**
     * Sets the item that the entity has it has on its foot
     * 
     * @param item the item to be set
     */
    void setBoots(IPlatformItem item);

}
