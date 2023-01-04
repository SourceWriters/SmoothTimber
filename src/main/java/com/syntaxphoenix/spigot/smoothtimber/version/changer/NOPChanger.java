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
    public boolean hasCuttingItemInHand(final Player player) {
        return false;
    }

    @Override
    public boolean hasPermissionForCuttingItem(final Player player, final ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack removeDurabilityFromItem(final ItemStack stack) {
        return stack;
    }

    @Override
    public void setItemInPlayerHand(final Player player, final ItemStack stack) {

    }

    @Override
    public boolean isWoodBlockImpl(final Block block) {
        return false;
    }

    @Override
    public void setupConfig() {

    }

    @Override
    public boolean hasPermissionForWood(final Player p, final Block b) {
        return false;
    }

    @Override
    public boolean hasPermissionForWoodType(final Player p, final WoodType type) {
        return false;
    }

    @Override
    public ItemStack getItemInHand(final Player p) {
        return null;
    }

    @Override
    public ItemStack getItemFromBlock(final Block block) {
        return null;
    }

    @Override
    public ItemStack getItemFromFallingBlock(final FallingBlock block) {
        return null;
    }

    @Override
    public ItemStack getAirItem() {
        return null;
    }

    @Override
    public Entity toFallingBlock(final Block block) {
        return null;
    }

    @Override
    public void setAirBlock(final Block block) {

    }

    @Override
    public EntityType getFallingBlockType() {
        return null;
    }

    @Override
    public int getMaxDropCount(final ItemStack tool) {
        return 0;
    }

    @Override
    public byte getData(final Block block) {
        return 0;
    }

    @Override
    public WoodType getWoodType(final Material type, final int id) {
        return null;
    }

    @Override
    public WoodType getWoodTypeFromBlock(final Block block) {
        return null;
    }

    @Override
    public boolean isSupported(final WoodType type) {
        return false;
    }

}
