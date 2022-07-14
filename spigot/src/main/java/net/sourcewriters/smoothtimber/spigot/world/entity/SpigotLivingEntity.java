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
        return new SpigotEquipment(entity.getEquipment());
    }

    @Override
    public boolean isInvisible() {
        return helper.isInvisible(entity);
    }

    @Override
    public void setInvisible(boolean invisible) {
        helper.setInvisible(entity, invisible);
    }

    @Override
    public boolean isCollidable() {
        return entity.isCollidable();
    }

    @Override
    public void setCollidable(boolean collidable) {
        entity.setCollidable(collidable);
    }

}
