package net.sourcewriters.smoothtimber.api.platform.world;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformArmorStand;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntity;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformFallingBlock;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformItemEntity;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.util.math.Vector3d;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

public interface IPlatformWorld {

    /**
     * Gets the handle of this world
     * 
     * @return the handle
     */
    Object getHandle();

    /**
     * Gets all online players which are currently in this world
     * 
     * @return the players
     */
    IPlatformPlayer[] getPlayers();

    /**
     * Gets the block at a specific position in this world
     * 
     * @param  x the x coordinate
     * @param  y the y coordinate
     * @param  z the z coordinate
     * 
     * @return   the block at the specified position
     */
    IPlatformBlock getBlock(int x, int y, int z);

    /**
     * Gets the block at a specific position in this world
     * 
     * @param  vector the position of the block
     * 
     * @return        the block at the specified position
     */
    default IPlatformBlock getBlock(final Vector3i vector) {
        return getBlock(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Gets the block at a specific position in this world
     * 
     * @param  vector the position of the block
     * 
     * @return        the block at the specified position
     */
    default IPlatformBlock getBlock(final Vector3d vector) {
        return getBlock((int) vector.getX(), (int) vector.getY(), (int) vector.getZ());
    }

    /**
     * Sets the block at a specific position in this world
     * 
     * @param x   the x coordinate
     * @param y   the y coordinate
     * @param z   the z coordinate
     * @param key the namespaced key of the block that should be placed
     */
    void setBlock(int x, int y, int z, ResourceKey key);

    /**
     * Sets the block at a specific position in this world
     * 
     * @param vector the position that the block should be placed at
     * @param key    the namespaced key of the block that should be placed
     */
    default void setBlock(final Vector3i vector, final ResourceKey key) {
        setBlock(vector.getX(), vector.getY(), vector.getZ(), key);
    }

    /**
     * Sets the block at a specific position in this world
     * 
     * @param vector the position that the block should be placed at
     * @param key    the namespaced key of the block that should be placed
     */
    default void setBlock(final Vector3d vector, final ResourceKey key) {
        setBlock((int) vector.getX(), (int) vector.getY(), (int) vector.getZ(), key);
    }

    /**
     * Spawns the specified entity at the specified position in this world
     * 
     * @param  x   the x coordinate
     * @param  y   the y coordinate
     * @param  z   the z coordinate
     * @param  key the namespaced key of the entity that should be spawned
     * 
     * @return     the spawned entity or null if the entity doesn't exist
     */
    IPlatformEntity spawn(double x, double y, double z, ResourceKey key);

    /**
     * Spawns the specified entity at the specified position in this world
     * 
     * @param  vector the position that the entity should be spawned at
     * @param  key    the namespaced key of the entity that should be spawned
     * 
     * @return        the spawned entity or null if the entity doesn't exist
     */
    default IPlatformEntity spawn(final Vector3i vector, final ResourceKey key) {
        return spawn(vector.getX(), vector.getY(), vector.getZ(), key);
    }

    /**
     * Spawns the specified entity at the specified position in this world
     * 
     * @param  vector the position that the entity should be spawned at
     * @param  key    the namespaced key of the entity that should be spawned
     * 
     * @return        the spawned entity or null if the entity doesn't exist
     */
    default IPlatformEntity spawn(final Vector3d vector, final ResourceKey key) {
        return spawn(vector.getX(), vector.getY(), vector.getZ(), key);
    }

    /**
     * Spawns an armor stand at the specified position in this world
     * 
     * @param  x the x coordinate
     * @param  y the y coordinate
     * @param  z the z coordinate
     * 
     * @return   the spawned armor stand
     */
    IPlatformArmorStand spawnArmorStand(double x, double y, double z);

    /**
     * Spawns an armor stand at the specified position in this world
     * 
     * @param  vector the position that the falling block should be spawned at
     * 
     * @return        the spawned armor stand
     */
    default IPlatformArmorStand spawnArmorStand(final Vector3i vector) {
        return spawnArmorStand(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Spawns an armor stand at the specified position in this world
     * 
     * @param  vector the position that the falling block should be spawned at
     * 
     * @return        the spawned armor stand
     */
    default IPlatformArmorStand spawnArmorStand(final Vector3d vector) {
        return spawnArmorStand(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Spawns the a falling block entity at the specified position in this world
     * 
     * @param  x   the x coordinate
     * @param  y   the y coordinate
     * @param  z   the z coordinate
     * @param  key the namespaced key of the block type that the falling block
     *                 should have
     * 
     * @return     the spawned falling block entity or null if the block type
     *                 doesn't exist
     */
    IPlatformFallingBlock spawnFallingBlock(double x, double y, double z, ResourceKey key);

    /**
     * Spawns the a falling block entity at the specified position in this world
     * 
     * @param  vector the position that the falling block should be spawned at
     * @param  key    the namespaced key of the block type that the falling block
     *                    should have
     * 
     * @return        the spawned falling block entity or null if the block type
     *                    doesn't exist
     */
    default IPlatformFallingBlock spawnFallingBlock(final Vector3i vector, final ResourceKey key) {
        return spawnFallingBlock(vector.getX(), vector.getY(), vector.getZ(), key);
    }

    /**
     * Spawns the a falling block entity at the specified position in this world
     * 
     * @param  vector the position that the falling block should be spawned at
     * @param  key    the namespaced key of the block type that the falling block
     *                    should have
     * 
     * @return        the spawned falling block entity or null if the block type
     *                    doesn't exist
     */
    default IPlatformFallingBlock spawnFallingBlock(final Vector3d vector, final ResourceKey key) {
        return spawnFallingBlock(vector.getX(), vector.getY(), vector.getZ(), key);
    }

    /**
     * Drops an item at the specified position in this world
     * 
     * @param  x       the x coordinate
     * @param  y       the y coordinate
     * @param  z       the z coordinate
     * @param  item    the item to be dropped
     * @param  natural if the item should be dropped naturally or not
     * 
     * @return         the dropped item entity
     */
    IPlatformItemEntity dropItem(double x, double y, double z, final IPlatformItem item, final boolean natural);

    /**
     * Drops an item at the specified position in this world
     * 
     * @param  vector  the position that the item should be dropped at
     * @param  item    the item to be dropped
     * @param  natural if the item should be dropped naturally or not
     * 
     * @return         the dropped item entity
     */
    default IPlatformItemEntity dropItem(final Vector3i vector, final IPlatformItem item, final boolean natural) {
        return dropItem(vector.getX(), vector.getY(), vector.getZ(), item, natural);
    }

    /**
     * Drops an item at the specified position in this world
     * 
     * @param  vector  the position that the item should be dropped at
     * @param  item    the item to be dropped
     * @param  natural if the item should be dropped naturally or not
     * 
     * @return         the dropped item entity
     */
    default IPlatformItemEntity dropItem(final Vector3d vector, final IPlatformItem item, final boolean natural) {
        return dropItem(vector.getX(), vector.getY(), vector.getZ(), item, natural);
    }

}
