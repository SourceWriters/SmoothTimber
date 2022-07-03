package net.sourcewriters.smoothtimber.spigot.world.entity;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntity;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.util.math.Vector3d;
import net.sourcewriters.smoothtimber.spigot.SpigotConversionRegistry;

public class SpigotEntity<E extends Entity> implements IPlatformEntity {

    protected final E entity;

    public SpigotEntity(final E entity) {
        this.entity = entity;
    }

    @Override
    public void send(String message) {
        entity.sendMessage(message);
    }

    @Override
    public String getName() {
        return entity.getName();
    }

    @Override
    public boolean isPlayer() {
        return entity.getType() == EntityType.PLAYER;
    }

    @Override
    public E getHandle() {
        return entity;
    }

    @Override
    public boolean isPermitted(String permission) {
        return entity.hasPermission(permission);
    }

    @Override
    public ResourceKey getType() {
        return SpigotConversionRegistry.fromBukkit(entity.getType().getKey());
    }

    @Override
    public IPlatformWorld getWorld() {
        return null;
    }

    @Override
    public Vector3d getPosition() {
        Location location = entity.getLocation();
        return new Vector3d(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public boolean isDead() {
        return entity.isDead();
    }

    @Override
    public boolean isInvulnerable() {
        return entity.isInvulnerable();
    }

    @Override
    public void setInvulnerable(boolean invulnerable) {
        entity.setInvulnerable(invulnerable);
    }

    @Override
    public boolean hasGravity() {
        return entity.hasGravity();
    }

    @Override
    public void setGravity(boolean gravity) {
        entity.setGravity(gravity);
    }

    @Override
    public Vector3d getVelocity() {
        Vector vector = entity.getVelocity();
        return new Vector3d(vector.getX(), vector.getY(), vector.getZ());
    }

    @Override
    public void setVelocity(Vector3d velocity) {
        entity.setVelocity(new Vector(velocity.getX(), velocity.getY(), velocity.getZ()));
    }

    @Override
    public int getId() {
        return entity.getEntityId();
    }

    @Override
    public UUID getUniqueId() {
        return entity.getUniqueId();
    }

    @Override
    public void remove() {
        entity.remove();
    }

}
