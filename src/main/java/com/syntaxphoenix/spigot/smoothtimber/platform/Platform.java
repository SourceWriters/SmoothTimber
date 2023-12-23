package com.syntaxphoenix.spigot.smoothtimber.platform;

import org.bukkit.Location;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Container;
import com.syntaxphoenix.syntaxapi.reflection.ClassCache;

public abstract class Platform {

    private static final Container<Platform> platform = Container.of();

    public static Platform getPlatform() {
        if (platform.isPresent()) {
            return platform.get();
        }
        return platform.replace(createPlatform(SmoothTimber.get())).get();
    }

    public static void shutdown() {
        final Platform platform = Platform.platform.get();
        Platform.platform.replace(null);
        platform.internalShutdown();
    }

    private static Platform createPlatform(final SmoothTimber plugin) {
        if (ClassCache.getOptionalClass("io.papermc.paper.threadedregions.RegionizedServer")
            .or(() -> ClassCache.getOptionalClass("io.papermc.paper.threadedregions.RegionizedServerInitEvent")).isPresent()) {
            return new FoliaPlatform(plugin);
        }
        return new SpigotPlatform(plugin);
    }

    protected abstract void internalShutdown();

    public void regionalTask(final Location location, final Runnable runnable) {
        runnable.run();
    }

    public void regionalSyncTask(final Location location, final Runnable runnable) {
        syncTask(runnable);
    }

    public void regionalSyncTaskLater(final Location location, final Runnable runnable, final long delay) {
        syncTaskLater(runnable, delay);
    }

    public abstract void syncTask(Runnable runnable);

    public abstract void syncTaskLater(Runnable runnable, long delay);

    public abstract void syncTaskTimer(Runnable runnable, long delay, long repeat);

    public abstract void asyncTask(Runnable runnable);

    public abstract void asyncTaskLater(Runnable runnable, long delay);

    public abstract void asyncTaskTimer(Runnable runnable, long delay, long repeat);

}