package net.sourcewriters.smoothtimber.spigot.world.entity;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntity;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntityData;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.util.math.Vector3d;
import net.sourcewriters.smoothtimber.spigot.SpigotConversionRegistry;
import net.sourcewriters.smoothtimber.spigot.command.SpigotSender;
import net.sourcewriters.smoothtimber.spigot.world.SpigotWorld;

public class SpigotEntity<P extends IPlatformEntity, E extends Entity> extends SpigotSender<P, E> implements IPlatformEntity {

    protected final IPlatformEntityData<P> data;

    public SpigotEntity(final E entity) {
        super(entity);
        this.data = SpigotConversionRegistry.getEntityData(this);
    }

    @Override
    public IPlatformEntityData<P> getData() {
        return data;
    }

    @Override
    public ResourceKey getType() {
        return SpigotConversionRegistry.getKey(handle.getType());
    }

    @Override
    public IPlatformWorld getWorld() {
        return SpigotWorld.of(handle.getWorld());
    }
    
    @Override
    public Entity getHandle() {
        return handle;
    }

    @Override
    public Vector3d getPosition() {
        Location location = handle.getLocation();
        return new Vector3d(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public boolean isDead() {
        return handle.isDead();
    }

    @Override
    public boolean isInvulnerable() {
        return handle.isInvulnerable();
    }

    @Override
    public void setInvulnerable(boolean invulnerable) {
        handle.setInvulnerable(invulnerable);
    }

    @Override
    public boolean hasGravity() {
        return handle.hasGravity();
    }

    @Override
    public void setGravity(boolean gravity) {
        handle.setGravity(gravity);
    }

    @Override
    public Vector3d getVelocity() {
        Vector vector = handle.getVelocity();
        return new Vector3d(vector.getX(), vector.getY(), vector.getZ());
    }

    @Override
    public void setVelocity(Vector3d velocity) {
        handle.setVelocity(new Vector(velocity.getX(), velocity.getY(), velocity.getZ()));
    }

    @Override
    public int getId() {
        return handle.getEntityId();
    }

    @Override
    public UUID getUniqueId() {
        return handle.getUniqueId();
    }

    @Override
    public void remove() {
        handle.remove();
    }

}
