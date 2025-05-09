package com.syntaxphoenix.spigot.smoothtimber.compatibility.lands;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.reason.DefaultReason;

import lands.v2.api.LandsIntegration;
import lands.v2.api.flags.type.Flags;
import lands.v2.api.land.Area;


public final class LandsChopListener_v2 implements Listener {

    private final LandsIntegration integration;

    protected LandsChopListener_v2(final SmoothTimber smoothTimber) {
        this.integration = LandsIntegration.of(smoothTimber);
    }

    @EventHandler(ignoreCancelled = true)
    public void onChopEvent(final AsyncPlayerChopTreeEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        for (final Location location : event.getBlockLocations()) {
            final Area area = integration.getArea(location);
            if (area == null) {
                continue;
            }
            if (uuid.equals(area.getOwnerUID()) || area.hasRoleFlag(uuid, Flags.BLOCK_BREAK)) {
                event.setCancelled(true);
                event.setReason(DefaultReason.LANDS);
                return;
            }
        }
    }
}
