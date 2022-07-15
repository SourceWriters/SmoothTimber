package net.sourcewriters.smoothtimber.spigot.world.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItemMeta;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.spigot.SpigotConversionRegistry;

public final class SpigotItem implements IPlatformItem {

    private final ItemStack handle;

    private SpigotItem(final ItemStack handle) {
        this.handle = handle;
    }

    @Override
    public ItemStack getHandle() {
        return handle;
    }

    @Override
    public SpigotItem clone() {
        return new SpigotItem(handle.clone());
    }

    @Override
    public ResourceKey getType() {
        return SpigotConversionRegistry.fromBukkit(handle.getType().getKey());
    }

    @Override
    public boolean hasType(ResourceKey key) {
        return handle.getType().getKey().toString().equals(key.toResourceString());
    }

    @Override
    public boolean setType(ResourceKey key) {
        Material material = SpigotConversionRegistry.getMaterial(key);
        if (material == null) {
            return false;
        }
        handle.setType(material);
        return true;
    }

    @Override
    public int getAmount() {
        return handle.getAmount();
    }

    @Override
    public void setAmount(int amount) {
        handle.setAmount(amount);
    }

    @Override
    public int getMaxAmount() {
        return handle.getMaxStackSize();
    }

    @Override
    public int getMaxDurability() {
        return handle.getType().getMaxDurability();
    }

    @Override
    public IPlatformItemMeta meta() {
        return new SpigotItemMeta(this);
    }

    public static SpigotItem of(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        return new SpigotItem(itemStack);
    }

    public static ItemStack from(IPlatformItem item) {
        if (item == null) {
            return null;
        }
        return (ItemStack) item.getHandle();
    }

}
