package net.sourcewriters.smoothtimber.api.platform.world.inventory;

import java.util.List;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public interface IPlatformItemMeta {

    /**
     * Gets the model id of the item
     * 
     * @return the item model id
     */
    int getModel();

    /**
     * Checks if the item has a model id
     * 
     * @return if the item has a model id
     */
    boolean hasModel();

    /**
     * Sets the model id of the item
     * 
     * @param model the model id to be set
     */
    void setModel(int model);

    /**
     * Gets the name of the item
     * 
     * @return the item name
     */
    String getName();

    /**
     * Checks if the item has a name
     * 
     * @return if the item has a name
     */
    boolean hasName();

    /**
     * Sets the name of the item
     * 
     * @param name the name to be set
     */
    void setName(String name);

    /**
     * Gets the lore of the item
     * 
     * @return the item lore
     */
    List<String> getLore();

    /**
     * Checks if the item has a lore
     * 
     * @return if the item has a lore
     */
    boolean hasLore();

    /**
     * Sets the lore of the item
     * 
     * @param lore the lore to be set
     */
    void setLore(List<String> lore);

    /**
     * Checks if the item has enchantments
     * 
     * @return if the item is enchanted
     */
    boolean hasEnchantments();

    /**
     * Gets the enchantment level of a specific enchantment
     * 
     * @param  key the target enchantment
     * 
     * @return     the level of the enchantment
     */
    int getEnchantment(ResourceKey key);

    /**
     * Checks if the item is enchanted with the specified enchantment
     * 
     * @param  key the target enchantment
     * 
     * @return     if the enchantment is applied
     */
    boolean hasEnchantment(ResourceKey key);

    /**
     * Checks if the item is enchanted with a conflicting enchantment of the
     * specified enchantment
     * 
     * @param  key the target enchantment
     * 
     * @return     if the item has a conflicting enchantment of the target
     *                 enchantment
     */
    boolean hasConflictingEnchantment(ResourceKey key);

    /**
     * Sets the enchantment level of the specified enchantment
     * 
     * @param key   the target enchantment
     * @param level the target level
     */
    void setEnchantment(ResourceKey key, int level);

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
     * Gets the durability of the item
     * 
     * @return the durability of the item
     */
    int getDurability();

    /**
     * Sets the durability of the item
     * 
     * @param durability the target durability
     */
    void setDurability(int durability);

    /**
     * Gets the damage of the item
     * 
     * @return the damage of the item
     */
    int getDamage();

    /**
     * Sets the damage of the item
     * 
     * @param damage the target damage
     */
    void setDamage(int damage);

    /**
     * Checks if the item is breakable
     * 
     * @return if the item is breakable
     */
    boolean isBreakable();

    /**
     * Checks if a flag is set
     * 
     * @return if the flag is set
     */
    boolean hasFlag(String name);

    /**
     * Applies the meta to the owning item
     * 
     * @return the item which owns this meta
     */
    IPlatformItem apply();

}
