package net.sourcewriters.smoothtimber.spigot.event;

import org.bukkit.event.Event;

import net.sourcewriters.smoothtimber.api.platform.event.PlatformEvent;
import net.sourcewriters.smoothtimber.api.platform.event.manager.UnsupportedEventDirectionException;
import net.sourcewriters.smoothtimber.core.SmoothTimberCore;
import net.sourcewriters.smoothtimber.core.util.JavaAccess;

public abstract class SpigotEvent<P extends PlatformEvent, B extends Event> {

    protected final Class<P> platformType;
    protected final Class<B> bukkitType;

    public SpigotEvent(Class<P> platformType, Class<B> bukkitType) {
        this.platformType = platformType;
        this.bukkitType = bukkitType;
    }

    public Class<B> getBukkitType() {
        return bukkitType;
    }

    public Class<P> getPlatformType() {
        return platformType;
    }

    public final P fromBukkitAbstract(SmoothTimberCore core, Event event) {
        if (!bukkitType.isAssignableFrom(event.getClass())) {
            return null;
        }
        return fromBukkit(core, bukkitType.cast(event));
    }

    public P fromBukkit(SmoothTimberCore core, B event) {
        throw new UnsupportedEventDirectionException(
            "Event '" + JavaAccess.getClassName(bukkitType) + "' cannot be converted into a platform event");
    }

    public final B toBukkitAbstract(SmoothTimberCore core, PlatformEvent event) {
        if (!platformType.isAssignableFrom(event.getClass())) {
            return null;
        }
        return toBukkit(core, platformType.cast(event));
    }

    public B toBukkit(SmoothTimberCore core, P event) {
        throw new UnsupportedEventDirectionException(
            "Event '" + JavaAccess.getClassName(platformType) + "' cannot be converted into a bukkit event");
    }

}
