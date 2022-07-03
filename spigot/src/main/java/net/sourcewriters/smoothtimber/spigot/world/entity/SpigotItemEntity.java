package net.sourcewriters.smoothtimber.spigot.world.entity;

import java.util.UUID;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformItemEntity;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.spigot.world.inventory.SpigotItem;

public final class SpigotItemEntity extends SpigotEntity<Item> implements IPlatformItemEntity {

    public SpigotItemEntity(Item entity) {
        super(entity);
    }

    @Override
    public int getPickupDelay() {
        return entity.getPickupDelay();
    }

    @Override
    public void setPickupDelay(int delay) {
        entity.setPickupDelay(delay);
    }

    @Override
    public IPlatformItem getItem() {
        return SpigotItem.of(entity.getItemStack());
    }

    @Override
    public void setItem(IPlatformItem item) {
        entity.setItemStack((ItemStack) item.getHandle());
    }

    @Override
    public UUID getOwner() {
        return entity.getOwner();
    }

}
