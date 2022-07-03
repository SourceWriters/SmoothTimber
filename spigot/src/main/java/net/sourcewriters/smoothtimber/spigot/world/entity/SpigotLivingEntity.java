package net.sourcewriters.smoothtimber.spigot.world.entity;

import org.bukkit.entity.LivingEntity;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformLivingEntity;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformEquipment;
import net.sourcewriters.smoothtimber.spigot.world.inventory.SpigotEquipment;

public class SpigotLivingEntity<E extends LivingEntity> extends SpigotEntity<E> implements IPlatformLivingEntity {

    public SpigotLivingEntity(E entity) {
        super(entity);
    }

    @Override
    public IPlatformEquipment getEquipment() {
        return new SpigotEquipment(entity.getEquipment());
    }

    @Override
    public boolean isInvisible() {
        return entity.isInvisible();
    }

    @Override
    public void setInvisible(boolean invisible) {
        entity.setInvisible(invisible);
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
