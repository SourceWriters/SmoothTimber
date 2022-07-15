package net.sourcewriters.smoothtimber.spigot.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import net.sourcewriters.smoothtimber.api.platform.event.*;
import net.sourcewriters.smoothtimber.api.platform.event.manager.PlatformEventManager;

public final class SpigotListener implements Listener {

    private final SpigotEventAdapter eventAdapter;
    private final PlatformEventManager eventManager;

    public SpigotListener(final SpigotEventAdapter eventAdapter, final PlatformEventManager eventManager) {
        this.eventAdapter = eventAdapter;
        this.eventManager = eventManager;
    }

    /*
     * TODO: Re-think listener design, maybe use a approach where the listeners are only registered if required
     *       and with the priority, so that we have more control internally
     */

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityChangeBlock(EntityChangeBlockEvent bukkitEvent) {
        PlatformEvent platform = eventAdapter.fromBukkit(bukkitEvent);
        if (platform == null || !(platform instanceof PlatformEntityChangeBlockEvent)) {
            return;
        }
        PlatformEntityChangeBlockEvent event = (PlatformEntityChangeBlockEvent) platform;
        eventManager.call(event, bukkitEvent.isAsynchronous());
        bukkitEvent.setCancelled(event.isCancelled());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent bukkitEvent) {
        PlatformEvent platform = eventAdapter.fromBukkit(bukkitEvent);
        if (platform == null || !(platform instanceof PlatformBlockBreakEvent)) {
            return;
        }
        PlatformBlockBreakEvent event = (PlatformBlockBreakEvent) platform;
        eventManager.call(event, bukkitEvent.isAsynchronous());
        bukkitEvent.setCancelled(event.isCancelled());
    }

}
