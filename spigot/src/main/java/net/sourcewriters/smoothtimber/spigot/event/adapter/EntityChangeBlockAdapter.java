package net.sourcewriters.smoothtimber.spigot.event.adapter;

import org.bukkit.event.entity.EntityChangeBlockEvent;

import net.sourcewriters.smoothtimber.api.platform.event.PlatformEntityChangeBlockEvent;
import net.sourcewriters.smoothtimber.core.SmoothTimberCore;
import net.sourcewriters.smoothtimber.spigot.SpigotConversionRegistry;
import net.sourcewriters.smoothtimber.spigot.event.SpigotEvent;
import net.sourcewriters.smoothtimber.spigot.world.SpigotBlock;

public final class EntityChangeBlockAdapter extends SpigotEvent<PlatformEntityChangeBlockEvent, EntityChangeBlockEvent> {

    public EntityChangeBlockAdapter() {
        super(PlatformEntityChangeBlockEvent.class, EntityChangeBlockEvent.class);
    }

    @Override
    public PlatformEntityChangeBlockEvent fromBukkit(SmoothTimberCore core, EntityChangeBlockEvent event) {
        return new PlatformEntityChangeBlockEvent(SpigotConversionRegistry.fromBukkit(event.getEntity()), new SpigotBlock(event.getBlock()),
            SpigotConversionRegistry.fromBukkit(event.getBlock().getType().getKey()),
            SpigotConversionRegistry.fromBukkit(event.getTo().getKey()));
    }

}
