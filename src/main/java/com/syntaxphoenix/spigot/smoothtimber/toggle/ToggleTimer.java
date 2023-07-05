package com.syntaxphoenix.spigot.smoothtimber.toggle;

import org.bukkit.plugin.Plugin;

import com.github.Anon8281.universalScheduler.scheduling.tasks.MyScheduledTask;
import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;

public class ToggleTimer implements Runnable {

    private final MyScheduledTask task;
    private final ToggleStorage storage;

    /*
     * 
     */

    public ToggleTimer(final ToggleStorage storage, final Plugin plugin) {
        this.storage = storage;
        this.task = SmoothTimber.getScheduler().runTaskTimerAsynchronously(this, 20L, 20L);
    }

    /*
     * 
     */

    public final MyScheduledTask getTask() {
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
