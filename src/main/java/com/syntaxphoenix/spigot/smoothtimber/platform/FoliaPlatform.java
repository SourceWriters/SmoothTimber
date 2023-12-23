package com.syntaxphoenix.spigot.smoothtimber.platform;

import java.util.concurrent.TimeUnit;

import org.bukkit.Location;
import org.bukkit.Server;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;

final class FoliaPlatform extends Platform {

    private final SmoothTimber plugin;

    private final GlobalRegionScheduler global;
    private final RegionScheduler region;
    private final AsyncScheduler async;

    public FoliaPlatform(final SmoothTimber plugin) {
        this.plugin = plugin;
        final Server server = plugin.getServer();
        final AbstractReflect reflect = new Reflect(server.getClass()).searchMethod("async", "getAsyncScheduler")
            .searchMethod("region", "getRegionScheduler").searchMethod("global", "getGlobalRegionScheduler");
        this.global = (GlobalRegionScheduler) reflect.run(server, "global");
        this.region = (RegionScheduler) reflect.run(server, "region");
        this.async = (AsyncScheduler) reflect.run(server, "async");
    }

    @Override
    protected void internalShutdown() {
        global.cancelTasks(plugin);
        async.cancelTasks(plugin);
    }

    @Override
    public void regionalTask(final Location location, final Runnable runnable) {
        region.execute(plugin, location, runnable);
    }

    @Override
    public void regionalSyncTask(final Location location, final Runnable runnable) {
        region.run(plugin, location, t -> runnable.run());
    }

    @Override
    public void regionalSyncTaskLater(final Location location, final Runnable runnable, final long delay) {
        region.runDelayed(plugin, location, t -> runnable.run(), delay);
    }

    @Override
    public void syncTask(final Runnable runnable) {
        global.run(plugin, t -> runnable.run());
    }

    @Override
    public void syncTaskLater(final Runnable runnable, final long delay) {
        global.runDelayed(plugin, t -> runnable.run(), delay);
    }

    @Override
    public void syncTaskTimer(final Runnable runnable, final long delay, final long repeat) {
        global.runAtFixedRate(plugin, t -> runnable.run(), delay, repeat);
    }

    @Override
    public void asyncTask(final Runnable runnable) {
        async.runNow(plugin, t -> runnable.run());
    }

    @Override
    public void asyncTaskLater(final Runnable runnable, final long delay) {
        async.runDelayed(plugin, t -> runnable.run(), delay * 50L, TimeUnit.MILLISECONDS);
    }

    @Override
    public void asyncTaskTimer(final Runnable runnable, final long delay, final long repeat) {
        async.runAtFixedRate(plugin, t -> runnable.run(), delay * 50L, repeat * 50L, TimeUnit.MILLISECONDS);
    }

}
