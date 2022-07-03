package net.sourcewriters.smoothtimber.api.platform.util;

/**
 * The item flags a item can have
 */
public enum STItemFlag {

    /**
     * Setting to show/hide enchants
     */
    ENCHANTMENTS,
    /**
     * Setting to show/hide Attributes like Damage
     */
    ATTRIBUTES,
    /**
     * Setting to show/hide the unbreakable State
     */
    UNBREAKABLE,
    /**
     * Setting to show/hide what the ItemStack can break/destroy
     */
    DESTROYS,
    /**
     * Setting to show/hide where this ItemStack can be build/placed on
     */
    PLACED_ON,
    /**
     * Setting to show/hide potion effects, book and firework information, map
     * tooltips, and enchantments of enchanted books.
     */
    POTION_EFFECTS,
    /**
     * Setting to show/hide dyes from coloured leather armour
     */
    DYE;

}
