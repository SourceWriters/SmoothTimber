package net.sourcewriters.smoothtimber.api.tree;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;

public interface ITreePermissionManager {

    /**
     * Checks if the player is allowed to cut the tree
     * 
     * @param  player the player to be checked
     * @param  tree   the tree that is asked for
     * 
     * @return        if the player is permitted or not
     */
    boolean isPermitted(IPlatformPlayer player, ITree tree);

    /**
     * Checks if the player is allowed to cut the material type
     * 
     * @param  player the player to be checked
     * @param  type   the type that is asked for
     * 
     * @return        if the player is permitted or not
     */
    boolean isPermitted(IPlatformPlayer player, IWoodType type);

    /**
     * Checks if the player is allowed to use the cutter item
     * 
     * @param  player the player to be checked
     * @param  tree   the item that is asked for
     * 
     * @return        if the player is permitted or not
     */
    boolean isPermitted(IPlatformPlayer player, IPlatformItem item);

    /**
     * Checks if the player has a block limit set
     * 
     * @param  player the player to be checked
     * 
     * @return        if the player has a block limit or not
     */
    boolean hasLimit(IPlatformPlayer player);

    /**
     * Gets the block limit of a player
     * 
     * @param  player the player that the block limit should be retrieved from
     * 
     * @return        the block limit
     */
    int getLimit(IPlatformPlayer player);

}
