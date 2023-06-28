package com.syntaxphoenix.spigot.smoothtimber.toggle;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;

public class ToggleTimer implements Runnable {

    private final Object task;
    private final ToggleStorage storage;

    /*
     * 
     */

    public ToggleTimer(final ToggleStorage storage, final Plugin plugin) {
        this.storage = storage;
				if(SmoothTimber.isFolia()) {
					this.task = Bukkit.getServer().getAsyncScheduler().runAtFixedRate(plugin, value -> this.run(), 20L, 20L, TimeUnit.MILLISECONDS);
				} else {
					this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, 20, 20);
				}
    }

    /*
     * 
     */

    public final Object getTask() {
        return task;
    }

    public final ToggleStorage getStorage() {
        return storage;
    }

    /*
     * 
     */

    @Override
    public void run() {
        storage.update();
    }

}
