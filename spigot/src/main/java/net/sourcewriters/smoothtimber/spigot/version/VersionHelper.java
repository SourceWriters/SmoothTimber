package net.sourcewriters.smoothtimber.spigot.version;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.ItemMeta;

import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntity;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntityData;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.spigot.SmoothTimberSpigot;

public abstract class VersionHelper {

    private static final Container<VersionHelper> VERSION = Container.of();

    public static VersionHelper get() {
        if (!VERSION.isModifiable()) {
            return VERSION.get();
        }
        VersionHelper helper = null;
        switch (CoreVersion.VERSION) {
        case v1_13:
            helper = new Version1_13();
            break;
        case v1_14:
            helper = new Version1_14();
            break;
        case v1_15:
        case v1_16:
            helper = new Version1_15();
            break;
        case v1_17:
            helper = new Version1_17();
            break;
        case v1_18:
        case v1_19:
            helper = new Version1_18();
            break;
        }
        return VERSION.replace(helper).lock().get();
    }

    protected final SmoothTimberSpigot plugin = SmoothTimberSpigot.getPlugin(SmoothTimberSpigot.class);

    public abstract NamespacedKey getKey(EntityType type);

    public abstract NamespacedKey getKey(Material type);

    public abstract NamespacedKey convert(ResourceKey key);

    public abstract void setBlock(World world, int x, int y, int z, BlockData data);

    public abstract boolean isInvisible(LivingEntity entity);

    public abstract void setInvisible(LivingEntity entity, boolean invisible);

    public abstract int getCustomModel(ItemMeta meta);

    public abstract boolean hasCustomModel(ItemMeta meta);

    public abstract void setCustomModel(ItemMeta meta, int model);
    
    public abstract <P extends IPlatformEntity> IPlatformEntityData<P> getEntityData(P entity);

}
