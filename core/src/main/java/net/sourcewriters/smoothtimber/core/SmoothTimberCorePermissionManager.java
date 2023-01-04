package net.sourcewriters.smoothtimber.core;

import net.sourcewriters.smoothtimber.api.ISmoothTimberPermissionManager;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.tree.IBlockType;
import net.sourcewriters.smoothtimber.api.tree.ITree;

public class SmoothTimberCorePermissionManager implements ISmoothTimberPermissionManager {

    @Override
    public boolean isPermitted(IPlatformPlayer player, ITree tree) {
        
        return false;
    }

    @Override
    public boolean isPermitted(IPlatformPlayer player, IBlockType type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isPermitted(IPlatformPlayer player, IPlatformItem item) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasLimit(IPlatformPlayer player) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getLimit(IPlatformPlayer player) {
        // TODO Auto-generated method stub
        return 0;
    }

}
