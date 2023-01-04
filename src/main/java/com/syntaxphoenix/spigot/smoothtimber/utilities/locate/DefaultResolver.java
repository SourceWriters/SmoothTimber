package com.syntaxphoenix.spigot.smoothtimber.utilities.locate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.limit.IntCounter;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;

public final class DefaultResolver extends LocationResolver {

    public static final DefaultResolver INSTANCE = new DefaultResolver();

    private DefaultResolver() {}

    @Override
    public List<Location> resolve(final Location start, final int radius, final List<Location> current, final IntCounter counter,
        final int limit, final boolean ignore) {
        final VersionChanger change = PluginUtils.CHANGER;
        final World world = start.getWorld();
        final int x = start.getBlockX();
        final int y = start.getBlockY();
        final int z = start.getBlockZ();

        final ArrayList<Location> resolved = new ArrayList<>();
        for (int cx = x - radius; cx <= x + radius; cx++) {
            for (int cz = z - radius; cz <= z + radius; cz++) {
                if (limit >= 0 && counter.get() >= limit) {
                    return resolved;
                }
                final Location location = new Location(world, cx, y, cz);
                if (change.isWoodBlock(Locator.getBlock(location))) {
                    if (current.contains(location)) {
                        continue;
                    }
                    current.add(location);
                    counter.increment();
                    resolved.add(location);
                }
            }
        }
        return resolved;
    }

    @Override
    public boolean isPlayerPlaced(final Location location) {
        return false;
    }

}
