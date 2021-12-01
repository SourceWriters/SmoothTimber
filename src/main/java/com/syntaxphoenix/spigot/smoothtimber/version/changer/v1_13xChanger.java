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
    public boolean hasCuttingItemInHand(Player player) {
        return CutterConfig.CUTTER_MATERIALS.contains(getItemInHand(player).getType().name());
    }

    @Override
    public boolean hasPermissionForCuttingItem(Player player, ItemStack item) {
        if (!CutterConfig.ENABLE_CUTTER_PERMISSIONS || item == null) {
            return true;
        }
        return VersionExchanger.checkPermission(item.getType().name().toLowerCase(), player);
    }

    @Override
    public byte getData(Block block) {
        return 0;
    }

    @Override
    public ItemStack removeDurabilityFromItem(ItemStack stack) {
        if (CutterConfig.ENABLE_UNBREAKING) {
            int level = stack.getEnchantmentLevel(Enchantment.DURABILITY);
            float chance = 100 / (level <= 0 ? 1 : (level + 1));
            if (RANDOM.nextFloat(0, 100) > chance) {
                return stack;
            }
        }
        ItemMeta meta = stack.getItemMeta();
        if (meta instanceof Damageable) {
            Damageable dmg = (Damageable) meta;
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
    public int getMaxDropCount(ItemStack stack) {
        int level = stack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        return level <= 0 ? 1 : level + 1;
    }

    @Override
    public void setItemInPlayerHand(Player player, ItemStack stack) {
        player.getEquipment().setItemInMainHand(stack);
    }

    @Override
    public boolean isWoodBlockImpl(Block block) {
        Material material = block.getBlockData().getMaterial();

        if (CutterConfig.ENABLE_EXCLUSION && CutterConfig.EXCLUDED_MATERIALS.contains(material)) {
            return false;
        }

        return getWoodType(material) != null;
    }

    @Override
    public void setupConfig() {
        CutterConfig.CUTTER_MATERIALS.addAll(Lists.asList("WOODEN_AXE", "STONE_AXE", "IRON_AXE", "GOLDEN_AXE", "DIAMOND_AXE"));
    }

    @Override
    public boolean hasPermissionForWood(Player p, Block b) {
        return hasPermissionForWoodType(p, getWoodType(b.getBlockData().getMaterial()));
    }

    @Override
    public boolean hasPermissionForWoodType(Player p, WoodType type) {
        if (!CutterConfig.ENABLE_WOOD_PERMISSIONS || type == null) {
            return true;
        }
        return VersionExchanger.checkPermission(type, p);
    }

    @Override
    public ItemStack getItemInHand(Player p) {
        return p.getEquipment().getItemInMainHand();
    }

    @Override
    public ItemStack getAirItem() {
        return new ItemStack(Material.AIR);
    }

    @Override
    public Entity toFallingBlock(Block block) {
        BlockData data = block.getBlockData();
        block.setType(Material.AIR);
        return block.getWorld().spawnFallingBlock(block.getLocation().add(0.5, 0.2, 0.5), data);
    }

    @Override
    public EntityType getFallingBlockType() {
        return EntityType.FALLING_BLOCK;
    }

    @Override
    public void dropItemByFallingBlock(FallingBlock block, int amount) {
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(block.getBlockData().getMaterial(), amount));
    }

    @Override
    public boolean isSupported(WoodType type) {
        switch (type) {
        case WARPED:
        case CRIMSON:
            return false;
        default:
            return true;
        }
    }

    @Override
    public WoodType getWoodTypeFromBlock(Block block) {
        return getWoodType(block.getBlockData().getMaterial());
    }

    @Override
    public WoodType getWoodType(Material type, int id) {
        return switch (type) {
            case OAK_LOG, OAK_WOOD, OAK_FENCE, STRIPPED_OAK_LOG, STRIPPED_OAK_WOOD -> WoodType.OAK;
            case SPRUCE_LOG, SPRUCE_WOOD, SPRUCE_FENCE, STRIPPED_SPRUCE_LOG, STRIPPED_SPRUCE_WOOD -> WoodType.SPRUCE;
            case BIRCH_LOG, BIRCH_WOOD, BIRCH_FENCE, STRIPPED_BIRCH_LOG, STRIPPED_BIRCH_WOOD -> WoodType.BIRCH;
            case JUNGLE_LOG, JUNGLE_WOOD, JUNGLE_FENCE, STRIPPED_JUNGLE_LOG, STRIPPED_JUNGLE_WOOD -> WoodType.JUNGLE;
            case DARK_OAK_LOG, DARK_OAK_WOOD, DARK_OAK_FENCE, STRIPPED_DARK_OAK_LOG, STRIPPED_DARK_OAK_WOOD -> WoodType.DARKOAK;
            case ACACIA_LOG, ACACIA_WOOD, ACACIA_FENCE, STRIPPED_ACACIA_LOG, STRIPPED_ACACIA_WOOD -> WoodType.ACACIA;
            default -> null;
        };
    }

}
