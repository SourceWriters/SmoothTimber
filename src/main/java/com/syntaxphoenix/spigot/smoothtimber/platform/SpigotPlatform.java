package com.syntaxphoenix.spigot.smoothtimber.platform;

import org.bukkit.scheduler.BukkitScheduler;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;

final class SpigotPlatform extends Platform {

    private final SmoothTimber plugin;
    private final BukkitScheduler scheduler;

    public SpigotPlatform(SmoothTimber plugin){
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
    }

    @Override
    protected void internalShutdown() {
        scheduler.cancelTasks(plugin);
    }

    @Override
    public void syncTask(Runnable runnable) {
        scheduler.runTask(plugin, runnable);
    }

    @Override
    public void syncTaskLater(Runnable runnable, long delay) {
        scheduler.runTaskLater(plugin, runnable, delay);
    }

    @Override
    public void syncTaskTimer(Runnable runnable, long delay, long repeat) {
        scheduler.runTaskTimer(plugin, runnable, delay, repeat);
    }

    @Override
    public void asyncTask(Runnable runnable) {
        scheduler.runTaskAsynchronously(plugin, runnable);
    }

    @Override
    public void asyncTaskLater(Runnable runnable, long delay) {
        scheduler.runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    @Override
    public void asyncTaskTimer(Runnable runnable, long delay, long repeat) {
        scheduler.runTaskTimerAsynchronously(plugin, runnable, delay, repeat);
    }
    
}
