package com.syntaxphoenix.spigot.smoothtimber.compatibility.logblock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.limit.IntCounter;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.LocationResolver;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;

import de.diddiz.LogBlock.LogBlock;

public class LogBlockResolver extends LocationResolver {

    private final ExecutorService executorService;
    private final LogBlockDatabaseAccessor databaseAccessor;

    public LogBlockResolver(final LogBlock plugin, final ExecutorService executorService) {
        this.executorService = executorService;
        this.databaseAccessor = new LogBlockDatabaseAccessor(plugin);
    }

    @Override
    public List<Location> resolve(final Location start, final int radius, final List<Location> current, final IntCounter counter,
        final int limit, final boolean ignore) {
        if (limit >= 0 && counter.get() >= limit) {
            return Collections.emptyList();
        }
        final VersionChanger change = PluginUtils.CHANGER;
        final World world = start.getWorld();
        final int x = start.getBlockX();
        final int y = start.getBlockY();
        final int z = start.getBlockZ();

        final List<Location> output = Collections.synchronizedList(new ArrayList<>());
        final List<Location> found = Collections.synchronizedList(new ArrayList<>());
        final ArrayList<Future<?>> tasks = new ArrayList<>();
        for (int cx = x - radius; cx <= x + radius; cx++) {
            for (int cz = z - radius; cz <= z + radius; cz++) {
                final Location location = new Location(world, cx, y, cz);
                final Block block = Locator.getBlock(location);
                if (change.isWoodBlock(block)) {
                    if (current.contains(location)) {
                        continue;
                    }
                    tasks.add(executorService.submit(() -> {
                        if (isPlayerPlaced(block)) {
                            return;
                        }
                        output.add(location);
                        found.add(location);
                    }));
                }
            }
        }
        for (final Future<?> task : tasks) {
            try {
                task.get(CutterConfig.GLOBAL_SYNC_TIME, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                // We just ignore it
            }
        }
        for (final Location location : output) {
            if (limit >= 0 && counter.get() >= limit) {
                found.remove(location);
                output.remove(location);
                continue;
            }
            counter.increment();
            current.add(location);
        }
        return new ArrayList<>(found);

    }

    public boolean isPlayerPlaced(final Block block) {
        if (block == null) {
            return false;
        }
        return isPlayerPlaced(block.getLocation());
    }

    @Override
    public boolean isPlayerPlaced(final Location location) {
        return databaseAccessor.isPlayerPlaced(location.getBlock());
    }

}
