package com.syntaxphoenix.spigot.smoothtimber.version.manager;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.syntaxapi.random.NumberGeneratorType;
import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

public interface VersionChanger {

    RandomNumberGenerator RANDOM = NumberGeneratorType.MURMUR.create(System.currentTimeMillis());

    default boolean isValid() {
        return true;
    }

    boolean hasCuttingItemInHand(Player player);

    boolean hasPermissionForCuttingItem(Player player, ItemStack stack);

    ItemStack removeDurabilityFromItem(ItemStack stack);

    void setItemInPlayerHand(Player player, ItemStack stack);

    boolean isWoodBlockImpl(Block block);

    default boolean isWoodBlock(final Block block) {
        return block != null && isWoodBlockImpl(block);
    }

    void setupConfig();

    boolean hasPermissionForWood(Player p, Block b);

    boolean hasPermissionForWoodType(Player p, WoodType type);

    ItemStack getItemInHand(Player p);

    ItemStack getItemFromBlock(Block block);

    ItemStack getItemFromFallingBlock(FallingBlock block);

    ItemStack getAirItem();

    Entity toFallingBlock(Block block);

    void setAirBlock(Block block);

    EntityType getFallingBlockType();

    default void dropItemByFallingBlock(final FallingBlock block, final int amount) {
        final ItemStack item = getItemFromFallingBlock(block);
        item.setAmount(amount);
        block.getWorld().dropItemNaturally(block.getLocation(), item);
    }

    default void dropItemByBlock(final Block block, final int amount) {
        final ItemStack item = getItemFromBlock(block);
        item.setAmount(amount);
        setAirBlock(block);
        block.getWorld().dropItemNaturally(block.getLocation(), item);
    }

    int getMaxDropCount(ItemStack tool);

    byte getData(Block block);

    WoodType getWoodType(Material type, int id);

    default WoodType getWoodType(final Material type) {
        return getWoodType(type, 0);
    }

    WoodType getWoodTypeFromBlock(Block block);

    boolean isSupported(WoodType type);

}
