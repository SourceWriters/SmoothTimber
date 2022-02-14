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

    public final ResourceKey getKey() {
        return key;
    }

    public abstract Map<IWoodType, Integer> cutTree(IPlatformPlayer player, IPlatformItem item, ITree tree, Vector3i relative);

}
