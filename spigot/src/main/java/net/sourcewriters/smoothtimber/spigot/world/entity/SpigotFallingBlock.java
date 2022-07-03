package net.sourcewriters.smoothtimber.spigot.world.entity;

import org.bukkit.entity.FallingBlock;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformFallingBlock;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;

public final class SpigotFallingBlock extends SpigotEntity<FallingBlock> implements IPlatformFallingBlock {

    public SpigotFallingBlock(FallingBlock entity) {
        super(entity);
    }

    @Override
    public IPlatformItem getItem() {
        // TODO: Add item drop
        return null;
    }
    
}
