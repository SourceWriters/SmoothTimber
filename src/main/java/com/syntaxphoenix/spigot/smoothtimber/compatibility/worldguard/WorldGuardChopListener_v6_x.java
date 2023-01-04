package com.syntaxphoenix.spigot.smoothtimber.compatibility.worldguard;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.reason.DefaultReason;
import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.ClassCache;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public final class WorldGuardChopListener_v6_x implements Listener {

    private final AbstractReflect sessionManager = new Reflect("com.sk89q.worldguard.session.SessionManager").searchMethod("bypass",
        "hasBypass", ClassCache.getClass("com.sk89q.worldguard.LocalPlayer"), ClassCache.getClass("com.sk89q.worldedit.world.World"));

    protected WorldGuardChopListener_v6_x() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onChopEvent(final AsyncPlayerChopTreeEvent event) {
        final WorldGuardPlugin plugin = WorldGuardPlugin.inst();
        final Object player = plugin.wrapOfflinePlayer(event.getPlayer());

        final boolean canBypass = (boolean) sessionManager.run(plugin.getSessionManager(), "bypass", player, event.getPlayer().getWorld());

        if (!canBypass) {
            final RegionQuery query = plugin.getRegionContainer().createQuery();
            for (final Location location : event.getBlockLocations()) {
                if (!query.testBuild(location, event.getPlayer())) {
                    event.setCancelled(true);
                    event.setReason(DefaultReason.WORLDGUARD);
                    break;
                }
            }
        }
    }

}
