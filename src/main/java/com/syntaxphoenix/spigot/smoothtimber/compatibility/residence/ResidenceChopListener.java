package com.syntaxphoenix.spigot.smoothtimber.compatibility.residence;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.reason.DefaultReason;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;

public final class ResidenceChopListener implements Listener {

    protected ResidenceChopListener() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onChopEvent(final AsyncPlayerChopTreeEvent event) {
        PluginUtils.getObjectFromMainThread(() -> {
            final ResidenceManager manager = Residence.getInstance().getResidenceManager();
            for (final Location location : event.getBlockLocations()) {
                final ClaimedResidence residence = manager.getByLoc(location);
                if (residence != null && !residence.getPermissions().playerHas(event.getPlayer(), Flags.build, true)) {
                    event.setCancelled(true);
                    event.setReason(DefaultReason.RESIDENCE);
                    return null;
                }
            }
            return null;
        }, 1000);
    }
}
