package net.sourcewriters.smoothtimber.api.platform.event;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;

public final class PlatformBlockBreakEvent extends PlatformEvent {

    private final IPlatformPlayer player;
    private final IPlatformBlock block;

    public PlatformBlockBreakEvent(IPlatformPlayer player, IPlatformBlock block) {
        this.player = player;
        this.block = block;
    }

    public IPlatformPlayer getPlayer() {
        return player;
    }

    public IPlatformBlock getBlock() {
        return block;
    }

}
