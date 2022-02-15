package net.sourcewriters.smoothtimber.api.tree;

import java.util.Map;
import java.util.Objects;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

public abstract class TreeAnimation {

    private final ResourceKey key;

    public TreeAnimation(SmoothTimberModule module, String name) {
        this.key = Objects.requireNonNull(module.key(name));
    }

    /**
     * Gets the id of the tree animation
     * 
     * @return the id key
     */
    public final ResourceKey getKey() {
        return key;
    }

    /**
     * Starts the tree cutting animation and returns a map which contains all wood
     * types provided in the tree and the amount of those types
     * 
     * @param  player   the player which is chopping the tree
     * @param  item     the cutter item used
     * @param  tree     the tree that is being cut
     * @param  relative the relative position of the cut
     * 
     * @return          the wood type and amount map
     */
    public abstract Map<IWoodType, Integer> cutTree(IPlatformPlayer player, IPlatformItem item, ITree tree, Vector3i relative);

}
