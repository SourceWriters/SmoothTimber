package com.syntaxphoenix.spigot.smoothtimber.version.changer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.WoodType;

public final class NOPChanger implements VersionChanger {
    
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public boolean hasCuttingItemInHand(Player player) {
        return false;
    }

    @Override
    public boolean hasPermissionForCuttingItem(Player player, ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack removeDurabilityFromItem(ItemStack stack) {
        return stack;
    }

    @Override
    public void setItemInPlayerHand(Player player, ItemStack stack) {
        
    }

    @Override
    public boolean isWoodBlockImpl(Block block) {
        return false;
    }

    @Override
    public void setupConfig() {
        
    }

    @Override
    public boolean hasPermissionForWood(Player p, Block b) {
        return false;
    }

    @Override
    public boolean hasPermissionForWoodType(Player p, WoodType type) {
        return false;
    }

    @Override
    public ItemStack getItemInHand(Player p) {
        return null;
    }

    @Override
    public ItemStack getItemFromBlock(Block block) {
        return null;
    }

    @Override
    public ItemStack getItemFromFallingBlock(FallingBlock block) {
        return null;
    }

    @Override
    public ItemStack getAirItem() {
        return null;
    }

    @Override
    public Entity toFallingBlock(Block block) {
        return null;
    }

    @Override
    public void setAirBlock(Block block) {
        
    }

    @Override
    public EntityType getFallingBlockType() {
        return null;
    }

    @Override
    public int getMaxDropCount(ItemStack tool) {
        return 0;
    }

    @Override
    public byte getData(Block block) {
        return 0;
    }

    @Override
    public WoodType getWoodType(Material type, int id) {
        return null;
    }

    @Override
    public WoodType getWoodTypeFromBlock(Block block) {
        return null;
    }

    @Override
    public boolean isSupported(WoodType type) {
        return false;
    }

}
