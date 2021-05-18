package com.syntaxphoenix.spigot.smoothtimber.compatibility.worldguard;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.reason.DefaultReason;

public final class WorldGuardChopListener_v6_x implements Listener {

    protected WorldGuardChopListener_v6_x() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onChopEvent(AsyncPlayerChopTreeEvent event) {
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(event.getPlayer());

        boolean canBypass = WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(localPlayer, localPlayer.getWorld());

        if (!canBypass) {
            RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
            for (Location location : event.getBlockLocations()) {
                com.sk89q.worldedit.util.Location worldLocation = new com.sk89q.worldedit.util.Location(localPlayer.getWorld(),
                    location.getX(), location.getY(), location.getZ());
                if (!query.testState(worldLocation, localPlayer, Flags.BUILD)) {
                    event.setCancelled(true);
                    event.setReason(DefaultReason.WORLDGUARD);
                    break;
                }
            }
        }
    }

}
