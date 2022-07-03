package net.sourcewriters.smoothtimber.spigot;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberExecutor;
import net.sourcewriters.smoothtimber.spigot.util.DoneFuture;
import net.sourcewriters.smoothtimber.spigot.util.TaskFuture;

public final class SpigotExecutor implements ISmoothTimberExecutor {

    private final BukkitScheduler scheduler;
    private final Plugin plugin;

    private final Consumer<Throwable> exceptionHandler = this::handleException;

    public SpigotExecutor(Plugin plugin) {
        this.scheduler = plugin.getServer().getScheduler();
        this.plugin = plugin;
    }

    @Override
    public Future<?> runAsync(Runnable task) {
        TaskFuture<Void> future = new TaskFuture<>(task);
        future.setExceptionHandler(exceptionHandler);
        scheduler.runTaskAsynchronously(plugin, future);
        return future;
    }

    @Override
    public <E> Future<E> supplyAsync(Callable<E> task) {
        TaskFuture<E> future = new TaskFuture<>(task);
        future.setExceptionHandler(exceptionHandler);
        scheduler.runTaskAsynchronously(plugin, future);
        return future;
    }

    @Override
    public Future<?> runSync(Runnable task) {
        if (Bukkit.isPrimaryThread()) {
            try {
                task.run();
            } catch (Throwable throwable) {
                handleException(throwable);
            }
            return DoneFuture.empty();
        }
        TaskFuture<Void> future = new TaskFuture<>(task);
        future.setExceptionHandler(exceptionHandler);
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
                handleException(throwable);
            }
            return DoneFuture.of(value);
        }
        TaskFuture<E> future = new TaskFuture<>(task);
        future.setExceptionHandler(exceptionHandler);
        scheduler.runTask(plugin, future);
        return future;
    }

    private void handleException(Throwable throwable) {
        // TODO: Handle exception
    }

}
