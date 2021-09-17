package net.sourcewriters.smoothtimber.core.platform;

import net.sourcewriters.smoothtimber.core.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.core.util.math.Vector3d;
import net.sourcewriters.smoothtimber.core.util.math.Vector3i;

public interface IPlatformWorld {

    IPlatformBlock getBlock(int x, int y, int z);

    default IPlatformBlock getBlock(Vector3i vector) {
        return getBlock(vector.getX(), vector.getY(), vector.getZ());
    }

    default IPlatformBlock getBlock(Vector3d vector) {
        return getBlock((int) vector.getX(), (int) vector.getY(), (int) vector.getZ());
    }

    void setBlock(int x, int y, int z, IPlatformBlock block);

    default void setBlock(Vector3i vector, IPlatformBlock block) {
        setBlock(vector.getX(), vector.getY(), vector.getZ(), block);
    }

    default void setBlock(Vector3d vector, IPlatformBlock block) {
        setBlock((int) vector.getX(), (int) vector.getY(), (int) vector.getZ(), block);
    }

    IPlatformEntity spawn(int x, int y, int z, ResourceKey resource);

    default IPlatformEntity spawn(Vector3i vector, ResourceKey resource) {
        return spawn(vector.getX(), vector.getY(), vector.getZ(), resource);
    }

    default IPlatformEntity spawn(Vector3d vector, ResourceKey resource) {
        return spawn((int) vector.getX(), (int) vector.getY(), (int) vector.getZ(), resource);
    }

    IPlatformFallingBlock spawnFallingBlock(int x, int y, int z, ResourceKey resource);

    default IPlatformFallingBlock spawnFallingBlock(Vector3i vector, ResourceKey resource) {
        return spawnFallingBlock(vector.getX(), vector.getY(), vector.getZ(), resource);
    }

    default IPlatformFallingBlock spawnFallingBlock(Vector3d vector, ResourceKey resource) {
        return spawnFallingBlock((int) vector.getX(), (int) vector.getY(), (int) vector.getZ(), resource);
    }

}
