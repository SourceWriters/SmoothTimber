package com.syntaxphoenix.spigot.smoothtimber.compatibility.blockylog;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import com.syntaxphoenix.blocky.log.BlockyWorld;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.limit.IntCounter;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.LocationResolver;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;

public final class BlockyLogResolver_v1_x extends LocationResolver {

    protected BlockyLogResolver_v1_x() {}

    @Override
    public List<Location> resolve(Location start, int radius, List<Location> current, IntCounter counter, int limit, boolean ignore) {
        VersionChanger change = PluginUtils.CHANGER;
        World world = start.getWorld();
        int x = start.getBlockX();
        int y = start.getBlockY();
        int z = start.getBlockZ();

        BlockyWorld blockyWorld = BlockyWorld.get(world);

        ArrayList<Location> resolved = new ArrayList<>();
        for (int cx = x - radius; cx <= x + radius; cx++) {
            for (int cz = z - radius; cz <= z + radius; cz++) {
                if (limit >= 0 && counter.get() >= limit) {
                    return resolved;
                }
                Location location = new Location(world, cx, y, cz);
                if (change.isWoodBlock(Locator.getBlock(location))) {
                    if (current.contains(location)) {
                        continue;
                    }
                    Chunk chunk = location.getChunk();
                    if (blockyWorld.containsChunk(chunk.getX(), chunk.getZ())
                        && blockyWorld.getChunk(chunk.getX(), chunk.getZ()).containsBlock(cx, y, cz)) {
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
    public boolean isPlayerPlaced(Location location) {
        BlockyWorld world = BlockyWorld.get(location.getWorld());
        Chunk chunk = location.getChunk();
        return !world.containsChunk(chunk.getX(), chunk.getZ())
            || world.getChunk(chunk.getX(), chunk.getZ()).containsBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

}
