package net.sourcewriters.smoothtimber.api.tree;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public interface IBlockType {

    /**
     * Gets the id of the block type
     * 
     * @return the id
     */
    ResourceKey id();

    /**
     * Gets the name of the block type
     * 
     * @return the name
     */
    default String name() {
        return id().getKey();
    }

    /**
     * Gets the block id of the block type that should be used in the animation
     * 
     * @return the name
     */
    ResourceKey key();

    /**
     * Gets if the block type is supported in the current context
     * 
     * @return if the block type is supported or not
     */
    boolean isSupported();

    /**
     * Checks if the block type is considered a wood
     * 
     * @return if the block type is a wood block type
     */
    boolean isWood();

    /**
     * Checks if the provided key is related to this wood type
     * 
     * @param  key the key to be checked
     * 
     * @return     if the key is related or not
     */
    boolean isType(ResourceKey key);

}
