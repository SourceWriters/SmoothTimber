package net.sourcewriters.smoothtimber.spigot.event;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;

import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberExecutor;
import net.sourcewriters.smoothtimber.api.platform.event.PlatformEvent;
import net.sourcewriters.smoothtimber.api.platform.event.manager.IPlatformEventAdapter;
import net.sourcewriters.smoothtimber.api.platform.event.manager.UnknownEventException;
import net.sourcewriters.smoothtimber.core.util.JavaAccess;
import net.sourcewriters.smoothtimber.spigot.SmoothTimberSpigot;

public final class SpigotEventAdapter implements IPlatformEventAdapter {

    private final ConcurrentHashMap<Class<? extends PlatformEvent>, SpigotEvent<?, ?>> platformToAdapter = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Class<? extends Event>, SpigotEvent<?, ?>> bukkitToAdapter = new ConcurrentHashMap<>();

    private final SmoothTimberSpigot plugin;
    private final PluginManager pluginManager = Bukkit.getPluginManager();

    private final ISmoothTimberExecutor executor;

    public SpigotEventAdapter(final SmoothTimberSpigot plugin) {
        this.plugin = plugin;
        this.executor = plugin.getCore().getApi().getPlatform().getExecutor();
    }

    public Event toBukkit(PlatformEvent platform) {
        if (platform == null) {
            return null;
        }
        SpigotEvent<?, ?> adapter = platformToAdapter.get(platform.getClass());
        if (adapter == null) {
            return null;
        }
        return adapter.toBukkitAbstract(plugin.getCore(), platform);
    }

    public PlatformEvent fromBukkit(Event bukkit) {
        if (bukkit == null) {
            return null;
        }
        SpigotEvent<?, ?> adapter = platformToAdapter.get(bukkit.getClass());
        if (adapter == null) {
            return null;
        }
        return adapter.fromBukkitAbstract(plugin.getCore(), bukkit);
    }

    public boolean register(SpigotEvent<?, ?> event) {
        if (event == null || platformToAdapter.containsKey(event.getPlatformType()) || bukkitToAdapter.containsKey(event.getBukkitType())) {
            return false;
        }
        platformToAdapter.put(event.getPlatformType(), event);
        bukkitToAdapter.put(event.getBukkitType(), event);
        return true;
    }

    public boolean unregisterBukkit(Class<? extends Event> bukkit) {
        SpigotEvent<?, ?> adapter = platformToAdapter.remove(bukkit);
        if (adapter == null) {
            return false;
        }
        bukkitToAdapter.remove(adapter.getPlatformType());
        return true;
    }

    public boolean unregisterPlatform(Class<? extends PlatformEvent> platform) {
        SpigotEvent<?, ?> adapter = bukkitToAdapter.remove(platform);
        if (adapter == null) {
            return false;
        }
        platformToAdapter.remove(adapter.getBukkitType());
        return true;
    }

    @Override
    public Future<?> call(PlatformEvent platform, boolean async) throws UnknownEventException {
        Event event = toBukkit(platform);
        if (event == null) {
            throw new UnknownEventException(
                "There is no implementation for the platform event '" + JavaAccess.getClassName(platform.getClass()) + "'!");
        }
        if (event.isAsynchronous()) {
            return executor.runAsync(() -> pluginManager.callEvent(event));
        }
        return executor.runSync(() -> pluginManager.callEvent(event));
    }

}
