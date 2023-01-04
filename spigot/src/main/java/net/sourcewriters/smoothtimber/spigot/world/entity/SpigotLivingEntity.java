package net.sourcewriters.smoothtimber.spigot.world.entity;

import org.bukkit.entity.LivingEntity;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformLivingEntity;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformEquipment;
import net.sourcewriters.smoothtimber.spigot.version.VersionHelper;
import net.sourcewriters.smoothtimber.spigot.world.inventory.SpigotEquipment;

public class SpigotLivingEntity<P extends IPlatformLivingEntity, E extends LivingEntity> extends SpigotEntity<P, E> implements IPlatformLivingEntity {
    
    private final VersionHelper helper = VersionHelper.get();

    public SpigotLivingEntity(E entity) {
        super(entity);
    }

    @Override
    public IPlatformEquipment getEquipment() {
        return new SpigotEquipment(handle.getEquipment());
    }

    @Override
    public boolean isInvisible() {
        return helper.isInvisible(handle);
    }

    @Override
    public void setInvisible(boolean invisible) {
        helper.setInvisible(handle, invisible);
    }

    @Override
    public boolean isCollidable() {
        return handle.isCollidable();
    }

    @Override
    public void setCollidable(boolean collidable) {
        handle.setCollidable(collidable);
    }

}
