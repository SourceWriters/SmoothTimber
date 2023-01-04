package com.syntaxphoenix.spigot.smoothtimber.version.changer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Lists;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionExchanger;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.WoodType;

public class v1_13xChanger implements VersionChanger {

    @Override
    public boolean hasCuttingItemInHand(final Player player) {
        return CutterConfig.CUTTER_MATERIALS.contains(getItemInHand(player).getType().name());
    }

    @Override
    public boolean hasPermissionForCuttingItem(final Player player, final ItemStack item) {
        if (!CutterConfig.ENABLE_CUTTER_PERMISSIONS || item == null) {
            return true;
        }
        return VersionExchanger.checkPermission(item.getType().name().toLowerCase(), player);
    }

    @Override
    public byte getData(final Block block) {
        return 0;
    }

    @Override
    public ItemStack removeDurabilityFromItem(final ItemStack stack) {
        if (CutterConfig.ENABLE_UNBREAKING) {
            final int level = stack.getEnchantmentLevel(Enchantment.DURABILITY);
            final float chance = 100 / (level <= 0 ? 1 : level + 1);
            if (RANDOM.nextFloat(0, 100) > chance) {
                return stack;
            }
        }
        final ItemMeta meta = stack.getItemMeta();
        if (meta.isUnbreakable()) {
            return stack;
        }
        if (meta instanceof Damageable) {
            final Damageable dmg = (Damageable) meta;
            int damage = dmg.getDamage() + 1;
            if (stack.getType().getMaxDurability() - damage < 0) {
                stack.setAmount(0);
                return null;
            }
            dmg.setDamage(damage = dmg.getDamage() + 1);
        }
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public int getMaxDropCount(final ItemStack stack) {
        final int level = stack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        return level <= 0 ? 1 : level + 1;
    }

    @Override
    public void setItemInPlayerHand(final Player player, final ItemStack stack) {
        player.getEquipment().setItemInMainHand(stack);
    }

    @Override
    public boolean isWoodBlockImpl(final Block block) {
        final Material material = block.getBlockData().getMaterial();

        if (CutterConfig.ENABLE_EXCLUSION && CutterConfig.EXCLUDED_MATERIALS.contains(material)) {
            return false;
        }

        if (CutterConfig.ENABLE_INCLUSION && CutterConfig.INCLUDED_MATERIALS.contains(material)) {
            return true;
        }

        return getWoodType(material) != WoodType.OTHER;
    }

    @Override
    public void setupConfig() {
        CutterConfig.CUTTER_MATERIALS.addAll(Lists.asList("WOODEN_AXE", "STONE_AXE", "IRON_AXE", "GOLDEN_AXE", "DIAMOND_AXE"));
    }

    @Override
    public boolean hasPermissionForWood(final Player p, final Block b) {
        return hasPermissionForWoodType(p, getWoodType(b.getBlockData().getMaterial()));
    }

    @Override
    public boolean hasPermissionForWoodType(final Player p, final WoodType type) {
        if (!CutterConfig.ENABLE_WOOD_PERMISSIONS || type == null) {
            return true;
        }
        return VersionExchanger.checkPermission(type, p);
    }

    @Override
    public ItemStack getItemInHand(final Player p) {
        return p.getEquipment().getItemInMainHand();
    }

    @Override
    public ItemStack getItemFromBlock(final Block block) {
        return new ItemStack(block.getBlockData().getMaterial());
    }

    @Override
    public ItemStack getItemFromFallingBlock(final FallingBlock block) {
        return new ItemStack(block.getBlockData().getMaterial());
    }

    @Override
    public ItemStack getAirItem() {
        return new ItemStack(Material.AIR);
    }

    @Override
    public void setAirBlock(final Block block) {
        block.setType(Material.AIR);
    }

    @Override
    public Entity toFallingBlock(final Block block) {
        final BlockData data = block.getBlockData();
        block.setType(Material.AIR);
        return block.getWorld().spawnFallingBlock(block.getLocation().add(0.5, 0.2, 0.5), data);
    }

    @Override
    public EntityType getFallingBlockType() {
        return EntityType.FALLING_BLOCK;
    }

    @Override
    public boolean isSupported(final WoodType type) {
        switch (type) {
        case OAK:
        case SPRUCE:
        case BIRCH:
        case JUNGLE:
        case ACACIA:
        case DARKOAK:
        case OTHER:
            return true;
        default:
            return false;
        }
    }

    @Override
    public WoodType getWoodTypeFromBlock(final Block block) {
        final Material blockMaterial = block.getBlockData().getMaterial();
        if (CutterConfig.ENABLE_INCLUSION && CutterConfig.INCLUDED_MATERIALS.contains(blockMaterial)) {
            return WoodType.OTHER;
        }
        return getWoodType(blockMaterial);
    }

    @Override
    public WoodType getWoodType(final Material type, final int id) {
        switch (type) {
        case OAK_LOG:
        case OAK_WOOD:
        case OAK_FENCE:
        case STRIPPED_OAK_LOG:
        case STRIPPED_OAK_WOOD:
            return WoodType.OAK;
        case SPRUCE_LOG:
        case SPRUCE_WOOD:
        case SPRUCE_FENCE:
        case STRIPPED_SPRUCE_LOG:
        case STRIPPED_SPRUCE_WOOD:
            return WoodType.SPRUCE;
        case BIRCH_LOG:
        case BIRCH_WOOD:
        case BIRCH_FENCE:
        case STRIPPED_BIRCH_LOG:
        case STRIPPED_BIRCH_WOOD:
            return WoodType.BIRCH;
        case JUNGLE_LOG:
        case JUNGLE_WOOD:
        case JUNGLE_FENCE:
        case STRIPPED_JUNGLE_LOG:
        case STRIPPED_JUNGLE_WOOD:
            return WoodType.JUNGLE;
        case DARK_OAK_LOG:
        case DARK_OAK_WOOD:
        case DARK_OAK_FENCE:
        case STRIPPED_DARK_OAK_LOG:
        case STRIPPED_DARK_OAK_WOOD:
            return WoodType.DARKOAK;
        case ACACIA_LOG:
        case ACACIA_WOOD:
        case ACACIA_FENCE:
        case STRIPPED_ACACIA_LOG:
        case STRIPPED_ACACIA_WOOD:
            return WoodType.ACACIA;
        default:
            return WoodType.OTHER;
        }
    }

}
