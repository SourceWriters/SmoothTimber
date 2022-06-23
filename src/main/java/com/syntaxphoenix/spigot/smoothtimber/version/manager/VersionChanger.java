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

    final RandomNumberGenerator RANDOM = NumberGeneratorType.MURMUR.create(System.currentTimeMillis());

    public default boolean isValid() {
        return true;
    }
    
    public boolean hasCuttingItemInHand(Player player);

    public boolean hasPermissionForCuttingItem(Player player, ItemStack stack);

    public ItemStack removeDurabilityFromItem(ItemStack stack);

    public void setItemInPlayerHand(Player player, ItemStack stack);

    public boolean isWoodBlockImpl(Block block);

    public default boolean isWoodBlock(Block block) {
        return block != null && isWoodBlockImpl(block);
    }

    public void setupConfig();

    public boolean hasPermissionForWood(Player p, Block b);

    public boolean hasPermissionForWoodType(Player p, WoodType type);

    public ItemStack getItemInHand(Player p);

    public ItemStack getItemFromBlock(Block block);

    public ItemStack getItemFromFallingBlock(FallingBlock block);

    public ItemStack getAirItem();

    public Entity toFallingBlock(Block block);

    public void setAirBlock(Block block);

    public EntityType getFallingBlockType();

    public default void dropItemByFallingBlock(FallingBlock block, int amount) {
        ItemStack item = getItemFromFallingBlock(block);
        item.setAmount(amount);
        block.getWorld().dropItemNaturally(block.getLocation(), item);
    }

    public default void dropItemByBlock(Block block, int amount) {
        ItemStack item = getItemFromBlock(block);
        item.setAmount(amount);
        setAirBlock(block);
        block.getWorld().dropItemNaturally(block.getLocation(), item);
    }

    public int getMaxDropCount(ItemStack tool);

    public byte getData(Block block);

    public WoodType getWoodType(Material type, int id);

    public default WoodType getWoodType(Material type) {
        return getWoodType(type, 0);
    }

    public WoodType getWoodTypeFromBlock(Block block);

    public boolean isSupported(WoodType type);

}
