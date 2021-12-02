package net.sourcewriters.smoothtimber.api.platform.world.entity;

import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformEquipment;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformInventory;

// TODO: Add documentation

public interface IPlatformLivingEntity extends IPlatformEntity {
    
    IPlatformEquipment getEquipment();
    
    IPlatformInventory getInventory();
    
    boolean isInvisible();
    
    void setInvisible(boolean invisible);
    
    boolean isCollidable();
    
    void setCollidable(boolean collidable);

}
