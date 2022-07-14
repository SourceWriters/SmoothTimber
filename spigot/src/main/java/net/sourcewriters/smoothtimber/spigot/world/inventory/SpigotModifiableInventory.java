package net.sourcewriters.smoothtimber.spigot.world.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.md_5.bungee.api.ChatColor;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.spigot.SpigotConversionRegistry;

public final class SpigotModifiableInventory extends SpigotInventory implements InventoryHolder {

    private Inventory inventory;

    private int size = InventoryType.CHEST.getDefaultSize();
    private String name = InventoryType.CHEST.getDefaultTitle();
    private InventoryType type = InventoryType.CHEST;

    public SpigotModifiableInventory() {
        this.inventory = Bukkit.createInventory(this, InventoryType.CHEST);
    }

    private void updateInventory() {
        if (type == InventoryType.CHEST) {
            this.inventory = Bukkit.createInventory(this, size, ChatColor.translateAlternateColorCodes('&', name));
            return;
        }
        this.inventory = Bukkit.createInventory(this, type, ChatColor.translateAlternateColorCodes('&', name));
    }

    @Override
    public boolean isModifiable() {
        return true;
    }

    @Override
    public Inventory getHandle() {
        return inventory;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean setName(String name) {
        if (name == null || name.isBlank() || this.name.equals(name)) {
            return false;
        }
        this.name = name;
        updateInventory();
        return true;
    }

    @Override
    public int getSize() {
        if (type == InventoryType.CHEST) {
            return size;
        }
        return inventory.getSize();
    }

    @Override
    public boolean setSize(int size) {
        if ((inventory.getType() == InventoryType.CHEST && inventory.getSize() == size)) {
            return false;
        }
        if (size < 0) {
            throw new IllegalStateException("Size has to be bigger than 0!");
        }
        if (size % 9 != 0) {
            throw new IllegalStateException("Size has to be divisible by 9!");
        }
        this.type = InventoryType.CHEST;
        this.size = size;
        updateInventory();
        return true;
    }

    @Override
    public ResourceKey getType() {
        return new ResourceKey("minecraft", type.name());
    }

    @Override
    public boolean setType(ResourceKey key) {
        InventoryType type = SpigotConversionRegistry.getInventory(key);
        if (type == null || type == inventory.getType()) {
            return false;
        }
        this.type = type;
        updateInventory();
        return true;
    }

}
