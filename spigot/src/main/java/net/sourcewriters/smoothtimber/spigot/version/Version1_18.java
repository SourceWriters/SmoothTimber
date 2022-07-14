package net.sourcewriters.smoothtimber.spigot.version;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.ItemMeta;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntity;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntityData;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

final class Version1_18 extends VersionHelper {

    @Override
    public NamespacedKey getKey(EntityType type) {
        return type.getKey();
    }

    @Override
    public NamespacedKey getKey(Material type) {
        return type.getKey();
    }

    @Override
    public NamespacedKey convert(ResourceKey key) {
        return NamespacedKey.fromString(key.toResourceString());
    }

    @Override
    public void setBlock(World world, int x, int y, int z, BlockData data) {
        world.setBlockData(x, y, z, data);
    }

    @Override
    public boolean isInvisible(LivingEntity entity) {
        return entity.isInvisible();
    }

    @Override
    public void setInvisible(LivingEntity entity, boolean invisible) {
        entity.setInvisible(true);
    }

    @Override
    public int getCustomModel(ItemMeta meta) {
        return meta.getCustomModelData();
    }

    @Override
    public boolean hasCustomModel(ItemMeta meta) {
        return meta.hasCustomModelData();
    }

    @Override
    public void setCustomModel(ItemMeta meta, int model) {
        meta.setCustomModelData(model);
    }

    @Override
    public <P extends IPlatformEntity> IPlatformEntityData<P> getEntityData(P entity) {
        return new EntityData1_14<>(plugin, entity);
    }

}
