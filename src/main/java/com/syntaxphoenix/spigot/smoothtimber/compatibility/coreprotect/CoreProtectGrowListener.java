package com.syntaxphoenix.spigot.smoothtimber.compatibility.coreprotect;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;

public class CoreProtectGrowListener implements Listener {

    private final CoreCompat compat;

    protected CoreProtectGrowListener(final CoreCompat compat) {
        this.compat = compat;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onGrow(final StructureGrowEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Player player = event.getPlayer();
        final String user = player != null ? "#st_" + player.getName() : "#tree";

        BukkitRunnable run = new BukkitRunnable() {
          @Override
          public void run()
          {
            for (final BlockState state : event.getBlocks()) {
              compat.logRemoval(user, state.getLocation(), state.getWorld().getBlockAt(state.getLocation()));
            }
          }
        };

        Bukkit.getRegionScheduler().runDelayed(SmoothTimber.get(), event.getLocation(), value -> run.run(), 5L);
    }

}
