package net.sourcewriters.smoothtimber.spigot.world.inventory;

import org.bukkit.inventory.EntityEquipment;

import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformEquipment;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;

public final class SpigotEquipment implements IPlatformEquipment {
    
    private final EntityEquipment equipment;
    
    public SpigotEquipment(final EntityEquipment equipment) {
        this.equipment = equipment;
    }

    @Override
    public IPlatformItem getMainHand() {
        return SpigotItem.of(equipment.getItemInMainHand());
    }

    @Override
    public void setMainHand(IPlatformItem item) {
        equipment.setItemInMainHand(SpigotItem.from(item));
    }

    @Override
    public IPlatformItem getOffHand() {
        return SpigotItem.of(equipment.getItemInOffHand());
    }

    @Override
    public void setOffHand(IPlatformItem item) {
        equipment.setItemInOffHand(SpigotItem.from(item));
    }

    @Override
    public IPlatformItem getHelmet() {
        return SpigotItem.of(equipment.getHelmet());
    }

    @Override
    public void setHelmet(IPlatformItem item) {
        equipment.setHelmet(SpigotItem.from(item));
    }

    @Override
    public IPlatformItem getChestplate() {
        return SpigotItem.of(equipment.getChestplate());
    }

    @Override
    public void setChestplate(IPlatformItem item) {
        equipment.setChestplate(SpigotItem.from(item));
    }

    @Override
    public IPlatformItem getLeggings() {
        return SpigotItem.of(equipment.getLeggings());
    }

    @Override
    public void setLeggings(IPlatformItem item) {
        equipment.setLeggings(SpigotItem.from(item));
    }

    @Override
    public IPlatformItem getBoots() {
        return SpigotItem.of(equipment.getBoots());
    }

    @Override
    public void setBoots(IPlatformItem item) {
        equipment.setBoots(SpigotItem.from(item));
    }

}
