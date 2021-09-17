package net.sourcewriters.smoothtimber.core.platform;

import net.sourcewriters.smoothtimber.core.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.core.util.math.Vector3d;
import net.sourcewriters.smoothtimber.core.util.math.Vector3i;

public interface IPlatformWorld {

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
    default IPlatformBlock getBlock(Vector3i vector) {
        return getBlock(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Gets the block at a specific position in this world
     * 
     * @param  vector the position of the block
     * 
     * @return        the block at the specified position
     */
    default IPlatformBlock getBlock(Vector3d vector) {
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
    default void setBlock(Vector3i vector, ResourceKey resource) {
        setBlock(vector.getX(), vector.getY(), vector.getZ(), resource);
    }

    /**
     * Sets the block at a specific position in this world
     * 
     * @param vector   the position that the block should be placed at
     * @param resource the namespaced key of the block that should be placed
     */
    default void setBlock(Vector3d vector, ResourceKey resource) {
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
    default IPlatformEntity spawn(Vector3i vector, ResourceKey resource) {
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
    default IPlatformEntity spawn(Vector3d vector, ResourceKey resource) {
        return spawn((int) vector.getX(), (int) vector.getY(), (int) vector.getZ(), resource);
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
    default IPlatformFallingBlock spawnFallingBlock(Vector3i vector, ResourceKey resource) {
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
    default IPlatformFallingBlock spawnFallingBlock(Vector3d vector, ResourceKey resource) {
        return spawnFallingBlock((int) vector.getX(), (int) vector.getY(), (int) vector.getZ(), resource);
    }

}
