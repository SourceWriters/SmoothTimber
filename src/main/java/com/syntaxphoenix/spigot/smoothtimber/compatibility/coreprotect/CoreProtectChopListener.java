package com.syntaxphoenix.spigot.smoothtimber.compatibility.coreprotect;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChoppedTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.platform.Platform;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;

public class CoreProtectChopListener implements Listener {

    private final ICoreCompat compat;

    protected CoreProtectChopListener(final ICoreCompat compat) {
        this.compat = compat;
    }

    @EventHandler
    public void onChopEvent(final AsyncPlayerChoppedTreeEvent event) {
        Platform platform = Platform.getPlatform();
        if (!platform.isRegional()) {
            handleChopEvent(event);
            return;
        }
        platform.regionalTask(event.getTreeLocation(), () -> handleChopEvent(event));
    }
    
    private void handleChopEvent(final AsyncPlayerChoppedTreeEvent event) {
        for (final Location location : event.getBlockLocations()) {
            BlockState state = Locator.getBlockState(location);
            if (state == null) {
                continue;
            }
            compat.logRemoval(event.getPlayer().getName(), location, state);
        }
    }

}
