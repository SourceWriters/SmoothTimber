package net.sourcewriters.smoothtimber.core.platform.world.entity;

import net.sourcewriters.smoothtimber.core.platform.world.inventory.IPlatformItem;

public interface IPlatformFallingBlock extends IPlatformEntity {

    /**
     * Gets the item corresponding to the block type of this entity
     * 
     * @return the item
     */
    IPlatformItem getItem();

}
