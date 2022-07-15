package net.sourcewriters.smoothtimber.spigot;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberExecutor;
import net.sourcewriters.smoothtimber.api.util.DoneFuture;
import net.sourcewriters.smoothtimber.api.util.TaskFuture;

public final class SpigotExecutor implements ISmoothTimberExecutor {

    private final BukkitScheduler scheduler;
    private final Plugin plugin;
    private final Logger logger;

    private final Consumer<Throwable> syncExceptionHandler = this::handleSyncException;
    private final Consumer<Throwable> asyncExceptionHandler = this::handleAsyncException;

    public SpigotExecutor(Plugin plugin) {
        this.scheduler = plugin.getServer().getScheduler();
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }

    @Override
    public Future<?> runAsync(Runnable task) {
        TaskFuture<Void> future = new TaskFuture<>(task);
        future.setExceptionHandler(asyncExceptionHandler);
        scheduler.runTaskAsynchronously(plugin, future);
        return future;
    }

    @Override
    public <E> Future<E> supplyAsync(Callable<E> task) {
        TaskFuture<E> future = new TaskFuture<>(task);
        future.setExceptionHandler(asyncExceptionHandler);
        scheduler.runTaskAsynchronously(plugin, future);
        return future;
    }

    @Override
    public Future<?> runSync(Runnable task) {
        if (Bukkit.isPrimaryThread()) {
            try {
                task.run();
            } catch (Throwable throwable) {
                handleSyncException(throwable);
            }
            return DoneFuture.empty();
        }
        TaskFuture<Void> future = new TaskFuture<>(task);
        future.setExceptionHandler(syncExceptionHandler);
        scheduler.runTask(plugin, future);
        return future;
    }

    @Override
    public <E> Future<E> supplySync(Callable<E> task) {
        if (Bukkit.isPrimaryThread()) {
            E value = null;
            try {
                value = task.call();
            } catch (Throwable throwable) {
                handleSyncException(throwable);
            }
            return DoneFuture.of(value);
        }
        TaskFuture<E> future = new TaskFuture<>(task);
        future.setExceptionHandler(syncExceptionHandler);
        scheduler.runTask(plugin, future);
        return future;
    }

    @Override
    public boolean isSync() {
        return Bukkit.isPrimaryThread();
    }

    private void handleSyncException(Throwable throwable) {
        logger.log(Level.WARNING, "Failed to execute synchronous task", throwable);
    }

    private void handleAsyncException(Throwable throwable) {
        logger.log(Level.WARNING, "Failed to execute asynchronous task", throwable);
    }

}
