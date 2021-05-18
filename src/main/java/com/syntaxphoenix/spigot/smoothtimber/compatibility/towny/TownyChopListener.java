package com.syntaxphoenix.spigot.smoothtimber.compatibility.towny;

import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.reason.DefaultReason;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownyChopListener implements Listener {

    private final Towny towny;

    public TownyChopListener(Towny towny) {
        this.towny = towny;
    }

    @EventHandler
    public void onAsyncPlayerChopTree(AsyncPlayerChopTreeEvent event) {
        for (Location location : event.getBlockLocations()) {
            if(!towny.canDestroy(event.getPlayer(), location.getBlock())) {
                event.setCancelled(true);
                event.setReason(DefaultReason.TOWNY);
                break;
            }
        }
    }

}
