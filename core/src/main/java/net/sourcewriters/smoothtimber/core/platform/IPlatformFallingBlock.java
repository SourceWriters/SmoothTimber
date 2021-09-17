package net.sourcewriters.smoothtimber.core.platform;

import net.sourcewriters.smoothtimber.core.util.math.Vector3i;

public interface IPlatformFallingBlock extends IPlatformEntity {
    
    void dropItemAt(IPlatformWorld world, Vector3i vector, int amount);

}
