package net.sourcewriters.smoothtimber.core.platform.world.entity;

import net.sourcewriters.smoothtimber.core.platform.world.inventory.IPlatformEquipment;
import net.sourcewriters.smoothtimber.core.platform.world.inventory.IPlatformInventory;

// TODO(Lauriichan): Add documentation

public interface IPlatformLivingEntity extends IPlatformEntity {
    
    IPlatformEquipment getEquipment();
    
    IPlatformInventory getInventory();
    
    boolean isInvisible();
    
    void setInvisible(boolean invisible);
    
    boolean isCollidable();
    
    void setCollidable(boolean collidable);

}
