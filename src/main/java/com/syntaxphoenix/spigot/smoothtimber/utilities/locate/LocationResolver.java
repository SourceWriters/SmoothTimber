package com.syntaxphoenix.spigot.smoothtimber.utilities.locate;

import java.util.List;

import org.bukkit.Location;

import com.syntaxphoenix.spigot.smoothtimber.utilities.limit.IntCounter;

public abstract class LocationResolver {

    public abstract List<Location> resolve(Location start, int radius, List<Location> current, IntCounter counter, int limit,
        boolean ignore);

    public List<Location> resolve(Location start, int radius, List<Location> current, IntCounter counter, int limit) {
        return resolve(start, radius, current, counter, limit, false);
    }

    public abstract boolean isPlayerPlaced(Location location);

}
