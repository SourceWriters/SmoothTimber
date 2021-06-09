package com.syntaxphoenix.spigot.smoothtimber.compatibility.worldguard;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.reason.DefaultReason;
import com.syntaxphoenix.syntaxapi.reflections.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflections.ClassCache;
import com.syntaxphoenix.syntaxapi.reflections.Reflect;

public final class WorldGuardChopListener_v6_x implements Listener {
    
    private AbstractReflect localPlayer = new Reflect("com.sk89q.worldguard.LocalPlayer").searchMethod("world", "getWorld");
    private AbstractReflect sessionManager = new Reflect("com.sk89q.worldguard.session.SessionManager").searchMethod("bypass", "hasBypass", localPlayer.getOwner(), ClassCache.getClass("com.sk89q.worldedit.world.World"));

    protected WorldGuardChopListener_v6_x() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onChopEvent(AsyncPlayerChopTreeEvent event) {
        WorldGuardPlugin plugin = WorldGuardPlugin.inst();
        Object player = plugin.wrapOfflinePlayer(event.getPlayer());
        
        boolean canBypass = (boolean) sessionManager.run(plugin.getSessionManager(), "bypass", player, localPlayer.run(player, "world"));

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
