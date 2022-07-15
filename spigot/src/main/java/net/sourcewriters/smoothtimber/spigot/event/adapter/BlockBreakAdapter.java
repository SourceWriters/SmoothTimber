package net.sourcewriters.smoothtimber.spigot.event.adapter;

import org.bukkit.event.block.BlockBreakEvent;

import net.sourcewriters.smoothtimber.api.platform.event.PlatformBlockBreakEvent;
import net.sourcewriters.smoothtimber.core.SmoothTimberCore;
import net.sourcewriters.smoothtimber.spigot.event.SpigotEvent;
import net.sourcewriters.smoothtimber.spigot.world.SpigotBlock;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotPlayer;

public final class BlockBreakAdapter extends SpigotEvent<PlatformBlockBreakEvent, BlockBreakEvent> {

    public BlockBreakAdapter() {
        super(PlatformBlockBreakEvent.class, BlockBreakEvent.class);
    }

    @Override
    public PlatformBlockBreakEvent fromBukkit(SmoothTimberCore core, BlockBreakEvent event) {
        return new PlatformBlockBreakEvent(new SpigotPlayer(event.getPlayer()), new SpigotBlock(event.getBlock()));
    }

}
