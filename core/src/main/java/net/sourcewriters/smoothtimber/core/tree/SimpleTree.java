package net.sourcewriters.smoothtimber.core.tree;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.tree.ITree;
import net.sourcewriters.smoothtimber.api.util.math.Cube;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

public final class SimpleTree implements ITree {

    private final IPlatformWorld world;
    private final IPlatformBlock[] blocks;
    private final ResourceKey key;

    public SimpleTree(final ResourceKey key, final IPlatformWorld world, final IPlatformBlock[] blocks) {
        this.key = key;
        this.world = world;
        this.blocks = blocks;
    }

    @Override
    public ResourceKey getId() {
        return key;
    }

    @Override
    public IPlatformBlock[] getBlocks(Vector3i relative, int limit) {
        // TODO: This is wrongly implemented like this; Adjust it to the intended design later
        if (limit <= 0) {
            limit = blocks.length;
        }
        IPlatformBlock[] array = new IPlatformBlock[Math.min(limit, blocks.length)];
        System.arraycopy(blocks, 0, array, 0, array.length);
        return array;
    }

    @Override
    public IPlatformWorld getWorld() {
        return world;
    }

    @Override
    public Cube getShape() {
        // TODO: Implement shape calculation somewhere
        return null;
    }

}
