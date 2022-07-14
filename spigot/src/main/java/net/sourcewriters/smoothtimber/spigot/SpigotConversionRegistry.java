package net.sourcewriters.smoothtimber.spigot;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;

import net.sourcewriters.smoothtimber.api.platform.util.STItemFlag;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntity;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntityData;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformLivingEntity;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.spigot.version.VersionHelper;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotArmorStand;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotEntity;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotFallingBlock;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotItemEntity;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotLivingEntity;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotPlayer;

public final class SpigotConversionRegistry {

    private static final SpigotConversionRegistry INSTANCE = new SpigotConversionRegistry();

    private final VersionHelper helper = VersionHelper.get();

    private final Map<String, EntityType> entityMap;
    private final Map<String, InventoryType> inventoryMap;
    private final ItemFlag[] itemFlags = ItemFlag.values();

    private SpigotConversionRegistry() {
        HashMap<String, EntityType> entityMap = new HashMap<>();
        for (EntityType type : EntityType.values()) {
            entityMap.put(helper.getKey(type).toString(), type);
        }
        this.entityMap = Collections.unmodifiableMap(entityMap);
        HashMap<String, InventoryType> inventoryMap = new HashMap<>();
        for (InventoryType type : InventoryType.values()) {
            inventoryMap.put(type.name().toLowerCase(), type);
        }
        this.inventoryMap = Collections.unmodifiableMap(inventoryMap);
    }
    
    @SuppressWarnings("unchecked")
    public static <P extends IPlatformEntity> IPlatformEntityData<P> getEntityData(SpigotEntity<P, ?> entity) {
        return INSTANCE.helper.getEntityData((P) entity);
    }

    public static ItemFlag getFlag(STItemFlag flag) {
        return INSTANCE.itemFlags[flag.ordinal()];
    }
    
    public static InventoryType getInventory(ResourceKey key) {
        return INSTANCE.inventoryMap.get(key.getKey());
    }

    public static EntityType getEntity(ResourceKey key) {
        return INSTANCE.entityMap.get(key.toResourceString());
    }

    public static Material getMaterial(ResourceKey key) {
        return Material.matchMaterial(key.toResourceString());
    }

    public static Enchantment getEnchantment(ResourceKey key) {
        return Enchantment.getByKey(toBukkit(key));
    }

    public static BlockData getBlockData(ResourceKey key) {
        try {
            return Bukkit.createBlockData(key.toResourceString());
        } catch (IllegalArgumentException iae) {
            return null;
        }
    }

    public static ResourceKey getKey(EntityType type) {
        return fromBukkit(INSTANCE.helper.getKey(type));
    }

    public static ResourceKey getKey(Material type) {
        return fromBukkit(INSTANCE.helper.getKey(type));
    }

    public static NamespacedKey toBukkit(ResourceKey key) {
        return INSTANCE.helper.convert(key);
    }

    public static ResourceKey fromBukkit(NamespacedKey key) {
        return new ResourceKey(key.getNamespace(), key.getKey());
    }

    public static SpigotEntity<?, ?> fromBukkit(Entity entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof LivingEntity) {
            if (entity instanceof ArmorStand) {
                return new SpigotArmorStand((ArmorStand) entity);
            }
            if (entity instanceof Player) {
                return new SpigotPlayer((Player) entity);
            }
            return new SpigotLivingEntity<IPlatformLivingEntity, LivingEntity>((LivingEntity) entity);
        }
        if (entity instanceof Item) {
            return new SpigotItemEntity((Item) entity);
        }
        if (entity instanceof FallingBlock) {
            return new SpigotFallingBlock((FallingBlock) entity);
        }
        return new SpigotEntity<IPlatformEntity, Entity>(entity);
    }

}
