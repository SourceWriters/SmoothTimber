package net.sourcewriters.smoothtimber.core.platform.world.entity;

import java.util.UUID;

import net.sourcewriters.smoothtimber.core.platform.command.IPlatformSender;
import net.sourcewriters.smoothtimber.core.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.core.util.math.Vector3d;

public interface IPlatformEntity extends IPlatformSender {

    /**
     * Get the namespaced key of the entity
     * 
     * @return the namespaced key
     */
    ResourceKey getType();

    /**
     * Get the position of the entity
     * 
     * @return the position
     */
    Vector3d getPosition();

    /**
     * Get the dead state of the entity
     * 
     * @return the dead state
     */
    boolean isDead();

    /**
     * Get the invulnerable state of the entity
     * 
     * @return the invulnerable state
     */
    boolean isInvulnerable();

    /**
     * Set the invulnerable state of the entity
     * 
     * @param invulnerable the invulnerable state
     */
    void setInvulnerable(boolean invulnerable);

    /**
     * Get the gravity state of the entity
     * 
     * @return the gravity state
     */
    boolean hasGravity();

    /**
     * Set the gravity state of the entity
     * 
     * @param invulnerable the invulnerable state
     */
    void setGravity(boolean gravity);

    /**
     * Get the velocity of the entity
     * 
     * @return the velocity
     */
    Vector3d getVelocity();

    /**
     * Set the velocity of the entity
     * 
     * @param velocity the velocity that should be applied
     */
    void setVelocity(Vector3d velocity);

    /**
     * Get the id of the entity
     * 
     * @return the id
     */
    int getId();

    /**
     * Get the unique id of the entity
     * 
     * @return the unique id
     */
    UUID getUniqueId();

    /**
     * Removes the entity entirely
     */
    void remove();

}
