package com.syntaxphoenix.spigot.smoothtimber.compatibility.griefprevention;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.reason.DefaultReason;
import com.syntaxphoenix.spigot.smoothtimber.platform.Platform;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

public final class GriefPreventionChopListener implements Listener {

    protected GriefPreventionChopListener() {

    }

    private final DataStore store = GriefPrevention.instance.dataStore;

    @EventHandler(ignoreCancelled = true)
    public void onChopEvent(final AsyncPlayerChopTreeEvent event) {
        final Player player = event.getPlayer();
        final UUID uniqueId = player.getUniqueId();
        if (store.getPlayerData(uniqueId).ignoreClaims) {
            return;
        }
        Platform.getPlatform().regionalSyncTask(event.getTreeLocation(), () -> {
            for (final Location location : event.getBlockLocations()) {
                final Claim claim = store.getClaimAt(location, false, null);
                if (claim == null) {
                    continue;
                }
                if (claim.allowBreak(player, location.getBlock().getType()) != null) {
                    event.setCancelled(true);
                    event.setReason(DefaultReason.GRIEFPREVENTION);
                    return;
                }
            }
        });
    }
}