package com.syntaxphoenix.spigot.smoothtimber.toggle;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.thread.Scheduler;

public class ToggleTimer implements Runnable {

    private final Object task;
    private final ToggleStorage storage;

    /*
     * 
     */

    public ToggleTimer(final ToggleStorage storage, final Plugin plugin) {
        this.storage = storage;
        if (SmoothTimber.IS_FOLIA) {
            this.task = Scheduler.runAsyncFixedRate(plugin, this, 20L, 20L);
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
