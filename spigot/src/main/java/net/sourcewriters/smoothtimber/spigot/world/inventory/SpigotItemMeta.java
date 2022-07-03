package net.sourcewriters.smoothtimber.spigot.world.inventory;

import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import net.sourcewriters.smoothtimber.api.platform.util.STItemFlag;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItemMeta;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.spigot.SpigotConversionRegistry;

public final class SpigotItemMeta implements IPlatformItemMeta {

    private final SpigotItem owner;
    private final ItemMeta meta;

    public SpigotItemMeta(final SpigotItem owner) {
        this.owner = owner;
        this.meta = owner.getHandle().getItemMeta();
    }

    @Override
    public int getModel() {
        return meta.getCustomModelData();
    }

    @Override
    public boolean hasModel() {
        return meta.hasCustomModelData();
    }

    @Override
    public void setModel(int model) {
        meta.setCustomModelData(model);
    }

    @Override
    public String getName() {
        return meta.getDisplayName();
    }

    @Override
    public boolean hasName() {
        return meta.hasDisplayName();
    }

    @Override
    public void setName(String name) {
        meta.setDisplayName(name);
    }

    @Override
    public List<String> getLore() {
        return meta.getLore();
    }

    @Override
    public boolean hasLore() {
        return meta.hasLore();
    }

    @Override
    public void setLore(List<String> lore) {
        meta.setLore(lore);
    }

    @Override
    public boolean hasEnchantments() {
        return meta.hasEnchants();
    }

    @Override
    public int getEnchantment(ResourceKey key) {
        return meta.getEnchantLevel(SpigotConversionRegistry.getEnchantment(key));
    }

    @Override
    public boolean hasEnchantment(ResourceKey key) {
        return meta.hasEnchant(SpigotConversionRegistry.getEnchantment(key));
    }

    @Override
    public boolean hasConflictingEnchantment(ResourceKey key) {
        return meta.hasConflictingEnchant(SpigotConversionRegistry.getEnchantment(key));
    }

    @Override
    public void setEnchantment(ResourceKey key, int level) {
        Enchantment enchantment = SpigotConversionRegistry.getEnchantment(key);
        if (meta.hasEnchant(enchantment)) {
            meta.removeEnchant(enchantment);
            if (level <= 0) {
                return;
            }
        }
        meta.addEnchant(enchantment, level, true);
    }

    @Override
    public int getDurability() {
        if (!isBreakable()) {
            return owner.getMaxDurability();
        }
        return owner.getMaxDurability() - ((Damageable) meta).getDamage();
    }

    @Override
    public void setDurability(int durability) {
        if (!isBreakable()) {
            return;
        }
        ((Damageable) meta).setDamage(owner.getMaxDurability() - durability);
    }

    @Override
    public int getDamage() {
        if (!isBreakable()) {
            return 0;
        }
        return ((Damageable) meta).getDamage();
    }

    @Override
    public void setDamage(int damage) {
        if (!isBreakable()) {
            return;
        }
        ((Damageable) meta).setDamage(damage);
    }

    @Override
    public boolean isBreakable() {
        return meta instanceof Damageable;
    }

    @Override
    public void setFlag(STItemFlag flag, boolean state) {
        
    }

    @Override
    public boolean hasFlag(STItemFlag name) {
        return false;
    }

    @Override
    public IPlatformItem apply() {
        ItemStack stack = owner.getHandle();
        stack.setItemMeta(meta);
        return owner;
    }

}
