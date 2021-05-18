/**
 * 
 * @author StevenLPHD
 * 
 */
package com.syntaxphoenix.spigot.smoothtimber.event;

import java.util.ArrayList;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.spigot.smoothtimber.event.reason.IChopReason;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;

public class AsyncPlayerChopTreeEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Location treeLocation;
    private final VersionChanger versionChange;
    private final ItemStack toolStack;
    private final ArrayList<Location> blocksToChop;
    private final int blockLimit;

    private IChopReason reason;

    public AsyncPlayerChopTreeEvent(Player player, Location treeLocation, VersionChanger version, ItemStack tool,
        ArrayList<Location> blocks, int limit) {
        super(true);
        this.player = player;
        this.treeLocation = treeLocation;
        this.versionChange = version;
        this.toolStack = tool;
        this.blocksToChop = blocks;
        this.blockLimit = limit;
    }

    private boolean cancelled = false;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public Optional<IChopReason> getReason() {
        return Optional.ofNullable(reason);
    }

    public void setReason(IChopReason reason) {
        this.reason = reason;
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

    public final ArrayList<Location> getBlockLocations() {
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
