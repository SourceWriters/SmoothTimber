package net.sourcewriters.smoothtimber.spigot.world.entity;

import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformFallingBlock;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.spigot.SpigotConversionRegistry;
import net.sourcewriters.smoothtimber.spigot.world.inventory.SpigotItem;

public final class SpigotFallingBlock extends SpigotEntity<IPlatformFallingBlock, FallingBlock> implements IPlatformFallingBlock {

    public SpigotFallingBlock(FallingBlock entity) {
        super(entity);
    }

    @Override
    public ResourceKey getBlockType() {
        return SpigotConversionRegistry.fromBukkit(entity.getBlockData().getMaterial().getKey());
    }

    @Override
    public IPlatformItem getItem() {
        return SpigotItem.of(new ItemStack(entity.getBlockData().getMaterial()));
    }

}
