package com.syntaxphoenix.spigot.smoothtimber.version.changer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.syntaxphoenix.spigot.smoothtimber.annotation.SupportedVersions;
import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Lists;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Storage;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionExchanger;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.WoodType;

@SupportedVersions({
    "1.9",
    "1.9.1",
    "1.9.2",
    "1.9.3",
    "1.9.4",
    "1.10",
    "1.10.1",
    "1.10.2",
})
@SuppressWarnings("deprecation")
public final class v1_9xChanger implements VersionChanger {

    private static final Material LOG = Material.valueOf("LOG");
    private static final Material LOG_2 = Material.valueOf("LOG_2");
    private static final Material FENCE = Material.valueOf("FENCE");

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
        return block.getData();
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
        if (stack.hasItemMeta()) {
            final ItemMeta meta = stack.getItemMeta();
            final Object spigot = Storage.META_SPIGOT.run(meta, "spigot");
            if ((Boolean) Storage.UNBREAKABLE.run(spigot, "unbreakable")) {
                return stack;
            }
        }
        final Integer durability = stack.getDurability() + 1;
        if (stack.getType().getMaxDurability() < durability) {
            stack.setAmount(0);
            return null;
        }
        stack.setDurability(durability.shortValue());
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
        final Material material = block.getType();

        if (CutterConfig.ENABLE_EXCLUSION && CutterConfig.EXCLUDED_MATERIALS.contains(material)) {
            return false;
        }

        if (CutterConfig.ENABLE_INCLUSION && CutterConfig.INCLUDED_MATERIALS.contains(material)) {
            return true;
        }

        return material == LOG || material == LOG_2 || material == FENCE;
    }

    @Override
    public void setupConfig() {
        CutterConfig.CUTTER_MATERIALS.addAll(Lists.asList("WOOD_AXE", "STONE_AXE", "IRON_AXE", "GOLD_AXE", "DIAMOND_AXE"));
    }

    @Override
    public boolean hasPermissionForWood(final Player p, final Block b) {
        return hasPermissionForWoodType(p, getWoodType(b.getType(), b.getData()));
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
        return getItemStack(block.getType(), block.getData(), 1);
    }

    @Override
    public ItemStack getItemFromFallingBlock(final FallingBlock block) {
        return getItemStack((Material) Storage.MATERIAL.run("type", Storage.FALLING_BLOCK.run(block, "id")),
            (byte) Storage.FALLING_BLOCK.run(block, "data"), 1);
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
        final Material type = block.getType();
        final byte data = block.getData();
        block.setType(Material.AIR);
        return block.getWorld().spawnFallingBlock(block.getLocation().add(0.5, 0.2, 0.5), type, data);
    }

    @Override
    public EntityType getFallingBlockType() {
        return EntityType.FALLING_BLOCK;
    }

    public ItemStack getItemStack(final Material type, final byte id, final int amount) {
        if (type == FENCE) {
            return new ItemStack(type, amount);
        }
        final WoodType wood = getWoodType(type, id);
        switch (wood) {
        case OAK:
            return new MaterialData(type, (byte) 0).toItemStack(amount);
        case SPRUCE:
            return new MaterialData(type, (byte) 1).toItemStack(amount);
        case BIRCH:
            return new MaterialData(type, (byte) 2).toItemStack(amount);
        case JUNGLE:
            return new MaterialData(type, (byte) 3).toItemStack(amount);
        case ACACIA:
            return new MaterialData(type, (byte) 1).toItemStack(amount);
        case DARKOAK:
            return new MaterialData(type, (byte) 0).toItemStack(amount);
        default:
            return new MaterialData(type, id).toItemStack(amount);
        }
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
        final Material blockMaterial = block.getType();
        if (CutterConfig.ENABLE_INCLUSION && CutterConfig.INCLUDED_MATERIALS.contains(blockMaterial)) {
            return WoodType.OTHER;
        }
        return getWoodType(blockMaterial, block.getData());
    }

    @Override
    public WoodType getWoodType(final Material type, final int id) {
        if (type == LOG) {
            switch (id) {
            case 0:
            case 4:
            case 8:
            case 12:
                return WoodType.OAK;
            case 1:
            case 5:
            case 9:
            case 13:
                return WoodType.SPRUCE;
            case 2:
            case 6:
            case 10:
            case 14:
                return WoodType.BIRCH;
            case 3:
            case 7:
            case 11:
            case 15:
                return WoodType.JUNGLE;
            }
        } else if (type == LOG_2) {
            switch (id) {
            case 0:
            case 2:
            case 4:
            case 6:
                return WoodType.DARKOAK;
            case 1:
            case 3:
            case 5:
            case 7:
                return WoodType.ACACIA;
            }
        } else if (type == FENCE) {
            return WoodType.OAK;
        }
        return WoodType.OTHER;
    }

}
