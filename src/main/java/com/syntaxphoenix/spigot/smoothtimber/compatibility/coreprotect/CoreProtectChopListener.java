package com.syntaxphoenix.spigot.smoothtimber.compatibility.coreprotect;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChoppedTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;

public class CoreProtectChopListener implements Listener {

    private final CoreCompat compat;

    protected CoreProtectChopListener(CoreCompat compat) {
        this.compat = compat;
    }

    @EventHandler
    public void onChopEvent(AsyncPlayerChoppedTreeEvent event) {
        for (Location location : event.getBlockLocations()) {
            compat.logRemoval(event.getPlayer().getName(), location, Locator.getBlock(location));
        }
    }

}
