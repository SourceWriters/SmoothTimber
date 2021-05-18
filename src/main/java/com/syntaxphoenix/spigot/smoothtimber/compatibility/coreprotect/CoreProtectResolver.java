package com.syntaxphoenix.spigot.smoothtimber.compatibility.coreprotect;

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
import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.limit.IntCounter;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.LocationResolver;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;

import net.coreprotect.CoreProtectAPI;

public class CoreProtectResolver extends LocationResolver {

    private final CoreProtectAPI api;
    private final ExecutorService service;

    protected CoreProtectResolver(ExecutorService service, Plugin plugin) {
        this.service = service;
        api = ((net.coreprotect.CoreProtect) plugin).getAPI();
    }

    @Override
    public List<Location> resolve(Location start, int radius, List<Location> current, IntCounter counter, int limit, boolean ignore) {
        if (limit >= 0 && counter.get() >= limit) {
            return Collections.emptyList();
        }
        VersionChanger change = PluginUtils.CHANGER;
        World world = start.getWorld();
        int x = start.getBlockX();
        int y = start.getBlockY();
        int z = start.getBlockZ();

        List<Location> output = Collections.synchronizedList(new ArrayList<>());
        List<Location> found = Collections.synchronizedList(new ArrayList<>());
        ArrayList<Future<?>> tasks = new ArrayList<>();
        for (int cx = x - radius; cx <= x + radius; cx++) {
            for (int cz = z - radius; cz <= z + radius; cz++) {
                Location location = new Location(world, cx, y, cz);
                Block block = Locator.getBlock(location);
                if (change.isWoodBlock(block)) {
                    if (current.contains(location)) {
                        continue;
                    }
                    tasks.add(service.submit(() -> {
                        if (isPlayerPlaced(block)) {
                            return;
                        }
                        output.add(location);
                        found.add(location);
                    }));
                }
            }
        }
        for (Future<?> task : tasks) {
            try {
                task.get(CutterConfig.GLOBAL_SYNC_TIME, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException ignored) {
                // We just ignore it
            }
        }
        for (Location location : output) {
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

    public boolean isPlayerPlaced(Block block) {
        List<String[]> list = api.blockLookup(block, 0);
        if (list == null || list.isEmpty()) {
            return false;
        }
        String[] array = list.get(0);
        CoreProtectAPI.ParseResult parseResult = api.parseResult(array);

        if (parseResult.getPlayer().isEmpty() || parseResult.getPlayer().startsWith("#")) {
            return false;
        }
        if (parseResult.isRolledBack()) {
            return false;
        }
        return parseResult.getActionId() != 0;
    }

    @Override
    public boolean isPlayerPlaced(Location location) {
        Block block = Locator.getBlock(location);
        if (block == null) {
            return false;
        }
        return isPlayerPlaced(block);
    }

}
