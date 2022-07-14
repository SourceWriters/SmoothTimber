package net.sourcewriters.smoothtimber.api.platform.world.entity;

import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;

public interface IPlatformItemEntity extends IPlatformEntity {

    IPlatformEntityData<IPlatformItemEntity> getData();
    
    int getPickupDelay();
    
    void setPickupDelay(int delay);
    
    IPlatformItem getItem();
    
    void setItem(IPlatformItem item);

}
