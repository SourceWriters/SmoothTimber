package net.sourcewriters.smoothtimber.api.tree;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;

public interface ITreePermissionManager {

    boolean isPermitted(IPlatformPlayer player, ITree tree);

    boolean isPermitted(IPlatformPlayer player, IWoodType type);

    boolean isPermitted(IPlatformPlayer player, IPlatformItem item);
    
    boolean hasLimit(IPlatformPlayer player);
    
    int getLimit(IPlatformPlayer player);
    
}
