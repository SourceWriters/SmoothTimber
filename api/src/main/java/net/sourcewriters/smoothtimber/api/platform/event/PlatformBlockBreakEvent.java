package net.sourcewriters.smoothtimber.api.platform.event;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;

public final class PlatformBlockBreakEvent extends PlatformEvent {

    private final IPlatformPlayer player;
    private final IPlatformBlock block;

    private boolean cancelled;

    public PlatformBlockBreakEvent(final IPlatformPlayer player, final IPlatformBlock block) {
        this.player = player;
        this.block = block;
    }

    /**
     * Gets the player that broke the block
     * 
     * @return the player
     */
    public IPlatformPlayer getPlayer() {
        return player;
    }

    /**
     * Gets the block that was broken
     * 
     * @return the block
     */
    public IPlatformBlock getBlock() {
        return block;
    }

    /**
     * Checks if the event is cancelled
     * 
     * @return if the event is cancelled or not
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancel state of the event
     * 
     * @param cancelled the state to set
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
