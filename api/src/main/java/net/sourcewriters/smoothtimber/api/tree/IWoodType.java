package net.sourcewriters.smoothtimber.api.tree;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public interface IWoodType {

    /**
     * Gets the name of the wood type
     * 
     * @return the name
     */
    String name();

    /**
     * Gets the block id of the wood type that should be used in the animation
     * 
     * @return the name
     */
    ResourceKey key();

    /**
     * Gets if the wood type is supported in the current context
     * 
     * @return if the wood type is supported or not
     */
    boolean isSupported();

    /**
     * Checks if the provided key is related to this wood type
     * 
     * @param  key the key to be checked
     * 
     * @return     if the key is related or not
     */
    boolean isType(ResourceKey key);

}
