package net.sourcewriters.smoothtimber.api.platform.event;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;

public final class PlatformBlockBreakEvent extends PlatformEvent {

    private final IPlatformPlayer player;
    private final IPlatformBlock block;

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

}
