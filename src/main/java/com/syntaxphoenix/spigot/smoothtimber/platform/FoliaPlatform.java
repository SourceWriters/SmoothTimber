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

    public FoliaPlatform(SmoothTimber plugin){
        this.plugin = plugin;
        Server server = plugin.getServer();
        AbstractReflect reflect = new Reflect(server.getClass()).searchMethod("async", "getAsyncScheduler").searchMethod("region", "getRegionScheduler").searchMethod("global", "getGlobalRegionScheduler");
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
    public void regionalTask(Location location, Runnable runnable) {
        region.execute(plugin, location, runnable);
    }

    @Override
    public void regionalSyncTask(Location location, Runnable runnable) {
        region.run(plugin, location, (t) -> runnable.run());
    }

    @Override
    public void regionalSyncTaskLater(Location location, Runnable runnable, long delay) {
        region.runDelayed(plugin, location, (t) -> runnable.run(), delay);
    }

    @Override
    public void syncTask(Runnable runnable) {
        global.run(plugin, (t) -> runnable.run());
    }

    @Override
    public void syncTaskLater(Runnable runnable, long delay) {
        global.runDelayed(plugin, (t) -> runnable.run(), delay);
    }

    @Override
    public void syncTaskTimer(Runnable runnable, long delay, long repeat) {
        global.runAtFixedRate(plugin, (t) -> runnable.run(), delay, repeat);
    }

    @Override
    public void asyncTask(Runnable runnable) {
        async.runNow(plugin, (t) -> runnable.run());
    }

    @Override
    public void asyncTaskLater(Runnable runnable, long delay) {
        async.runDelayed(plugin, (t) -> runnable.run(), delay * 50L, TimeUnit.MILLISECONDS);
    }

    @Override
    public void asyncTaskTimer(Runnable runnable, long delay, long repeat) {
        async.runAtFixedRate(plugin, (t) -> runnable.run(), delay * 50L, repeat * 50L, TimeUnit.MILLISECONDS);
    }
    
}
