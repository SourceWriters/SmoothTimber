package net.sourcewriters.smoothtimber.spigot.world.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformInventory;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public abstract class SpigotInventory implements IPlatformInventory {

    public abstract Inventory getHandle();

    @Override
    public void add(IPlatformItem item) {
        if (item == null) {
            return;
        }
        getHandle().addItem((ItemStack) item.getHandle());
    }

    @Override
    public void set(int slot, IPlatformItem item) {
        if (item == null) {
            getHandle().setItem(slot, null);
            return;
        }
        getHandle().setItem(slot, (ItemStack) item.getHandle());
    }

    @Override
    public IPlatformItem get(int slot) {
        return SpigotItem.of(getHandle().getItem(slot));
    }

    @Override
    public int getSize() {
        return getHandle().getSize();
    }

    @Override
    public ResourceKey getType() {
        return new ResourceKey("minecraft", getHandle().getType().name());
    }

}
