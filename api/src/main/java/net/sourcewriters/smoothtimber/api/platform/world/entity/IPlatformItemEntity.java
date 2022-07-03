package net.sourcewriters.smoothtimber.api.platform.world.entity;

import java.util.UUID;

import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;

public interface IPlatformItemEntity extends IPlatformEntity {
    
    int getPickupDelay();
    
    void setPickupDelay(int delay);
    
    IPlatformItem getItem();
    
    void setItem(IPlatformItem item);
    
    UUID getOwner();

}
