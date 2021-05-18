package com.syntaxphoenix.spigot.smoothtimber.compatibility.worldguard;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.reason.DefaultReason;

public final class WorldGuardChopListener_v5_x implements Listener {

    protected WorldGuardChopListener_v5_x() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onChopEvent(AsyncPlayerChopTreeEvent event) {
        WorldGuardPlugin plugin = WorldGuardPlugin.inst();
        LocalPlayer localPlayer = plugin.wrapOfflinePlayer(event.getPlayer());

        boolean canBypass = plugin.getSessionManager().hasBypass(localPlayer, localPlayer.getWorld());

        if (!canBypass) {
            RegionQuery query = plugin.getRegionContainer().createQuery();
            for (Location location : event.getBlockLocations()) {
                if (!query.testBuild(location, event.getPlayer())) {
                    event.setCancelled(true);
                    event.setReason(DefaultReason.WORLDGUARD);
                    break;
                }
            }
        }
    }

}
