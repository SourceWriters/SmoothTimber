package net.sourcewriters.smoothtimber.api.platform.world;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformArmorStand;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntity;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformFallingBlock;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.util.math.Vector3d;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

public interface IPlatformWorld {

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
     * @param x        the x coordinate
     * @param y        the y coordinate
     * @param z        the z coordinate
     * @param resource the namespaced key of the block that should be placed
     */
    void setBlock(int x, int y, int z, ResourceKey resource);

    /**
     * Sets the block at a specific position in this world
     * 
     * @param vector   the position that the block should be placed at
     * @param resource the namespaced key of the block that should be placed
     */
    default void setBlock(final Vector3i vector, final ResourceKey resource) {
        setBlock(vector.getX(), vector.getY(), vector.getZ(), resource);
    }

    /**
     * Sets the block at a specific position in this world
     * 
     * @param vector   the position that the block should be placed at
     * @param resource the namespaced key of the block that should be placed
     */
    default void setBlock(final Vector3d vector, final ResourceKey resource) {
        setBlock((int) vector.getX(), (int) vector.getY(), (int) vector.getZ(), resource);
    }

    /**
     * Spawns the specified entity at the specified position in this world
     * 
     * @param  x        the x coordinate
     * @param  y        the y coordinate
     * @param  z        the z coordinate
     * @param  resource the namespaced key of the entity that should be spawned
     * 
     * @return          the spawned entity or null if the entity doesn't exist
     */
    IPlatformEntity spawn(int x, int y, int z, ResourceKey resource);

    /**
     * Spawns the specified entity at the specified position in this world
     * 
     * @param  vector   the position that the entity should be spawned at
     * @param  resource the namespaced key of the entity that should be spawned
     * 
     * @return          the spawned entity or null if the entity doesn't exist
     */
    default IPlatformEntity spawn(final Vector3i vector, final ResourceKey resource) {
        return spawn(vector.getX(), vector.getY(), vector.getZ(), resource);
    }

    /**
     * Spawns the specified entity at the specified position in this world
     * 
     * @param  vector   the position that the entity should be spawned at
     * @param  resource the namespaced key of the entity that should be spawned
     * 
     * @return          the spawned entity or null if the entity doesn't exist
     */
    default IPlatformEntity spawn(final Vector3d vector, final ResourceKey resource) {
        return spawn((int) vector.getX(), (int) vector.getY(), (int) vector.getZ(), resource);
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
    IPlatformArmorStand spawnArmorStand(int x, int y, int z);

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
        return spawnArmorStand((int) vector.getX(), (int) vector.getY(), (int) vector.getZ());
    }

    /**
     * Spawns the a falling block entity at the specified position in this world
     * 
     * @param  x        the x coordinate
     * @param  y        the y coordinate
     * @param  z        the z coordinate
     * @param  resource the namespaced key of the block type that the falling block
     *                      should have
     * 
     * @return          the spawned falling block entity or null if the block type
     *                      doesn't exist
     */
    IPlatformFallingBlock spawnFallingBlock(int x, int y, int z, ResourceKey resource);

    /**
     * Spawns the a falling block entity at the specified position in this world
     * 
     * @param  vector   the position that the falling block should be spawned at
     * @param  resource the namespaced key of the block type that the falling block
     *                      should have
     * 
     * @return          the spawned falling block entity or null if the block type
     *                      doesn't exist
     */
    default IPlatformFallingBlock spawnFallingBlock(final Vector3i vector, final ResourceKey resource) {
        return spawnFallingBlock(vector.getX(), vector.getY(), vector.getZ(), resource);
    }

    /**
     * Spawns the a falling block entity at the specified position in this world
     * 
     * @param  vector   the position that the falling block should be spawned at
     * @param  resource the namespaced key of the block type that the falling block
     *                      should have
     * 
     * @return          the spawned falling block entity or null if the block type
     *                      doesn't exist
     */
    default IPlatformFallingBlock spawnFallingBlock(final Vector3d vector, final ResourceKey resource) {
        return spawnFallingBlock((int) vector.getX(), (int) vector.getY(), (int) vector.getZ(), resource);
    }

    /**
     * Drops an item at the specified position in this world
     * 
     * @param  item    the item to be dropped
     * @param  natural if the item should be dropped naturally or not
     * 
     * @return         the dropped item entity
     */
    IPlatformEntity dropItem(final IPlatformItem item, final boolean natural);

}
