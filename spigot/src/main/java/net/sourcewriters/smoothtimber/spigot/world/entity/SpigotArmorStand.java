package net.sourcewriters.smoothtimber.spigot.world.entity;

import org.bukkit.entity.ArmorStand;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformArmorStand;

public final class SpigotArmorStand extends SpigotLivingEntity<IPlatformArmorStand, ArmorStand> implements IPlatformArmorStand {

    public SpigotArmorStand(ArmorStand entity) {
        super(entity);
    }

    @Override
    public boolean isSmall() {
        return entity.isSmall();
    }

    @Override
    public void setSmall(boolean small) {
        entity.setSmall(small);
    }
    
}
