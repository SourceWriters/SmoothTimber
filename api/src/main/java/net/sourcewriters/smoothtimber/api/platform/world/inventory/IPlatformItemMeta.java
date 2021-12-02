package net.sourcewriters.smoothtimber.api.platform.world.inventory;

import java.util.List;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

// TODO: Add documentation

public interface IPlatformItemMeta {

    int getModel();

    boolean hasModel();

    void setModel(int model);

    String getName();

    boolean hasName();

    void setName(String name);

    List<String> getLore();

    boolean hasLore();

    void setLore(List<String> lore);

    boolean hasEnchantments();

    int getEnchantment(ResourceKey key);

    boolean hasEnchantment(ResourceKey key);

    boolean hasConflictingEnchantment(ResourceKey key);

    void setEnchantment(ResourceKey key, int level);

    int getAmount();

    void setAmount(int amount);

    int getDurability();

    void setDurability(int durability);

    int getDamage();

    void setDamage(int damage);

    boolean isBreakable();

    boolean hasFlag(String name);

    IPlatformItem apply();

}
