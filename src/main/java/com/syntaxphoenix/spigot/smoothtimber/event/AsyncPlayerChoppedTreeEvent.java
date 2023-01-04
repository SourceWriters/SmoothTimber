/**
 * 
 * @author StevenLPHD
 * 
 */
package com.syntaxphoenix.spigot.smoothtimber.event;

import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;

public class AsyncPlayerChoppedTreeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Location treeLocation;
    private final VersionChanger versionChange;
    private final ItemStack toolStack;
    private final List<Location> blocksToChop;
    private final int blockLimit;

    public AsyncPlayerChoppedTreeEvent(final Player player, final Location treeLocation, final VersionChanger version, final ItemStack tool,
        final List<Location> blocks, final int limit) {
        super(true);
        this.player = player;
        this.treeLocation = treeLocation;
        this.versionChange = version;
        this.toolStack = tool;
        this.blocksToChop = Collections.unmodifiableList(blocks);
        this.blockLimit = limit;
    }

    public final Player getPlayer() {
        return player;
    }

    public final int getBlockLimit() {
        return blockLimit;
    }

    public final ItemStack getToolStack() {
        return toolStack;
    }

    public final Location getTreeLocation() {
        return treeLocation;
    }

    public final VersionChanger getVersionUtil() {
        return versionChange;
    }

    public final List<Location> getBlockLocations() {
        return blocksToChop;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
