package net.sourcewriters.smoothtimber.core.animation;

import java.util.Map;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.tree.ITree;
import net.sourcewriters.smoothtimber.api.tree.IWoodType;
import net.sourcewriters.smoothtimber.api.tree.TreeAnimation;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

public final class DefaultAnimation extends TreeAnimation {

    public DefaultAnimation(SmoothTimberModule module) {
        super(module, "default");
    }

    @Override
    public Map<IWoodType, Integer> cutTree(IPlatformPlayer player, IPlatformItem item, ITree tree, Vector3i relative) {
        // TODO Auto-generated method stub
        return null;
    }

}
