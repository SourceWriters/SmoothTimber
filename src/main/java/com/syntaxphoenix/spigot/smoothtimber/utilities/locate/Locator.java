package com.syntaxphoenix.spigot.smoothtimber.utilities.locate;

import static com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig.GLOBAL_DEBUG;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Function;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.ListQueue;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.limit.IntCounter;

public abstract class Locator {

    private static Function<Location, Block> BLOCK_DETECTOR;
    private static LocationResolver LOCATION_RESOLVER = DefaultResolver.INSTANCE;

    public static void setSyncBlockDetection(final boolean sync) {
        BLOCK_DETECTOR = sync ? location -> {
            try {
                return PluginUtils.getObjectFromMainThread(() -> location.getBlock());
            } catch (final Exception ignore) {
                if (GLOBAL_DEBUG) {
                    PluginUtils.sendConsoleError("Something went wrong while detecting a block synchronously", ignore);
                }
                return null;
            }
        } : location -> {
            try {
                return Objects.requireNonNull(location.getBlock());
            } catch (final Exception execute) {
                try {
                    return PluginUtils.getObjectFromMainThread(() -> location.getBlock());
                } catch (final Exception ignore) {
                    if (GLOBAL_DEBUG) {
                        PluginUtils.sendConsoleError("Something went wrong while detecting a block synchronously", ignore);
                    }
                    return null;
                }
            }
        };
    }

    public static void setLocationResolver(final LocationResolver resolver) {
        LOCATION_RESOLVER = resolver == null ? DefaultResolver.INSTANCE : resolver;
    }

    public static LocationResolver getLocationResolver() {
        return LOCATION_RESOLVER;
    }

    public static Block getBlock(final Location location) {
        return BLOCK_DETECTOR.apply(location);
    }

    public static void locateWood(final Location breakPoint, final List<Location> output, final int limit) {
        int roots = CutterConfig.ROOT_DEPTH;
        final int radius = CutterConfig.CHECK_RADIUS;
        final int test = CutterConfig.CHECK_RADIUS / 2;
        final IntCounter counter = new IntCounter();
        final Queue<Location> layers = new ListQueue<>();
        layers.offer(new Location(breakPoint.getWorld(), breakPoint.getBlockX(), breakPoint.getBlockY() - roots, breakPoint.getBlockZ()));
        final LocationResolver resolver = LOCATION_RESOLVER;
        final Queue<Location> current = new ListQueue<>();
        final ArrayList<Location> testList = new ArrayList<>();
        final IntCounter testCounter = new IntCounter();
        while (counter.get() != limit) {
            if (layers.peek() == null) {
                if (roots != -1) {
                    layers.offer(new Location(breakPoint.getWorld(), breakPoint.getBlockX(), breakPoint.getBlockY() - roots,
                        breakPoint.getBlockZ()));
                    continue;
                }
                break;
            }
            current.offer(layers.poll());
            while (current.peek() != null) {
                final List<Location> resolve = resolver.resolve(current.poll(), radius, output, counter, limit);
                if (resolve == null) {
                    continue;
                }
                for (final Location location : resolve) {
                    current.offer(location);
                    testCounter.reset();
                    testList.clear();
                    final List<Location> above = resolver.resolve(
                        new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 1, location.getBlockZ()), test,
                        testList, testCounter, -1, true);
                    for (final Location tmp : above) {
                        layers.offer(tmp);
                    }
                }
            }
            if (roots != -1) {
                roots--;
            }
        }
    }

    public static boolean isPlayerPlaced(final Location location) {
        return LOCATION_RESOLVER.isPlayerPlaced(location);
    }
}
