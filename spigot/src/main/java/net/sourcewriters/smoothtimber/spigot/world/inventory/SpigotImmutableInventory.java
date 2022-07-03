package net.sourcewriters.smoothtimber.spigot.world.inventory;

import org.bukkit.inventory.Inventory;

public final class SpigotImmutableInventory extends SpigotInventory {

    private final Inventory inventory;

    public SpigotImmutableInventory(final Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Inventory getHandle() {
        return inventory;
    }

}
