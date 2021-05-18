package com.syntaxphoenix.spigot.smoothtimber.compatibility.coreprotect;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;

public class CoreProtectGrowListener implements Listener {

    private final CoreCompat compat;

    protected CoreProtectGrowListener(CoreCompat compat) {
        this.compat = compat;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onGrow(StructureGrowEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        String user = player != null ? "#st_" + player.getName() : "#tree";
        SmoothTimber.get().getServer().getScheduler().runTaskLaterAsynchronously(SmoothTimber.get(), () -> {
            for (BlockState state : event.getBlocks()) {
                compat.logRemoval(user, state.getLocation(), state.getBlock());
            }
        }, 5);
    }

}
