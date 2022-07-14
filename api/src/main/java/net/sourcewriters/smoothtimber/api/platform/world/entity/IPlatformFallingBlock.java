package net.sourcewriters.smoothtimber.api.platform.world.entity;

import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public interface IPlatformFallingBlock extends IPlatformEntity {

    IPlatformEntityData<IPlatformFallingBlock> getData();
    
    /**
     * Gets the block type
     * 
     * @return the block type key
     */
    ResourceKey getBlockType();

    /**
     * Gets the item corresponding to the block type of this entity
     * 
     * @return the item
     */
    IPlatformItem getItem();

}
