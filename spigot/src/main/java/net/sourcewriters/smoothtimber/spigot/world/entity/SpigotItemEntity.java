package net.sourcewriters.smoothtimber.spigot.world.entity;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformItemEntity;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.spigot.world.inventory.SpigotItem;

public final class SpigotItemEntity extends SpigotEntity<IPlatformItemEntity, Item> implements IPlatformItemEntity {

    public SpigotItemEntity(Item entity) {
        super(entity);
    }

    @Override
    public int getPickupDelay() {
        return handle.getPickupDelay();
    }

    @Override
    public void setPickupDelay(int delay) {
        handle.setPickupDelay(delay);
    }

    @Override
    public IPlatformItem getItem() {
        return SpigotItem.of(handle.getItemStack());
    }

    @Override
    public void setItem(IPlatformItem item) {
        handle.setItemStack((ItemStack) item.getHandle());
    }

}
