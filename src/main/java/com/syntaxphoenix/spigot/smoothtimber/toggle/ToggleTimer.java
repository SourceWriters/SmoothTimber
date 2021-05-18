package com.syntaxphoenix.spigot.smoothtimber.toggle;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class ToggleTimer implements Runnable {

    private final BukkitTask task;
    private final ToggleStorage storage;

    /*
     * 
     */

    public ToggleTimer(ToggleStorage storage, Plugin plugin) {
        this.storage = storage;
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, 20, 20);
    }

    /*
     * 
     */

    public final BukkitTask getTask() {
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
