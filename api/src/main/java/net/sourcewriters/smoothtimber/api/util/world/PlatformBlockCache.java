package net.sourcewriters.smoothtimber.api.util.world;

import java.util.concurrent.ConcurrentHashMap;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.util.Tuple;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

public final class PlatformBlockCache {

    private final ConcurrentHashMap<Vector3i, Tuple<IPlatformBlock, ResourceKey>> blocks = new ConcurrentHashMap<>();
    private final IPlatformWorld world;

    public PlatformBlockCache(final IPlatformWorld world) {
        this.world = world;
    }

    public IPlatformWorld getWorld() {
        return world;
    }

    public IPlatformBlock getBlock(int x, int y, int z) {
        return getBlock(new Vector3i(x, y, z));
    }

    public IPlatformBlock getBlock(Vector3i location) {
        return get(location).getFirst();
    }

    public ResourceKey getBlockType(int x, int y, int z) {
        return getBlockType(new Vector3i(x, y, z));
    }

    public ResourceKey getBlockType(Vector3i location) {
        return get(location).getSecond();
    }

    public Tuple<IPlatformBlock, ResourceKey> get(int x, int y, int z) {
        return get(new Vector3i(x, y, z));
    }

    public Tuple<IPlatformBlock, ResourceKey> get(Vector3i location) {
        return blocks.computeIfAbsent(location, loc -> {
            IPlatformBlock block = world.getBlock(location);
            return Tuple.of(block, block.getType());
        });
    }

}
