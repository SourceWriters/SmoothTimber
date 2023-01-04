package net.sourcewriters.smoothtimber.spigot.world;

import java.lang.ref.WeakReference;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformArmorStand;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntity;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformFallingBlock;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformItemEntity;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.spigot.SpigotConversionRegistry;
import net.sourcewriters.smoothtimber.spigot.version.VersionHelper;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotArmorStand;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotFallingBlock;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotItemEntity;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotPlayer;

public final class SpigotWorld implements IPlatformWorld {

    private static final ConcurrentHashMap<UUID, SpigotWorld> worlds = new ConcurrentHashMap<>();

    public static SpigotWorld of(World world) {
        if (world == null) {
            return null;
        }
        SpigotWorld spigot = worlds.get(world.getUID());
        if (spigot != null) {
            if (spigot.isValid()) {
                return spigot;
            }
            worlds.remove(world.getUID());
            return null;
        }
        worlds.put(world.getUID(), spigot = new SpigotWorld(world));
        return spigot;
    }

    private final UUID id;
    private final VersionHelper helper = VersionHelper.get();

    private WeakReference<World> world;

    SpigotWorld(final World world) {
        this.id = world.getUID();
        this.world = new WeakReference<>(world);
    }

    @Override
    public boolean isValid() {
        return getHandle() != null;
    }

    @Override
    public World getHandle() {
        if (world.get() != null) {
            return world.get();
        }
        World world = Bukkit.getWorld(id);
        if (world == null) {
            return null;
        }
        this.world = new WeakReference<>(world);
        return world;
    }

    @Override
    public IPlatformPlayer[] getPlayers() {
        Player[] players = getHandle().getPlayers().toArray(Player[]::new);
        IPlatformPlayer[] platform = new IPlatformPlayer[players.length];
        for (int index = 0; index < players.length; index++) {
            platform[index] = new SpigotPlayer(players[index]);
        }
        return platform;
    }

    @Override
    public IPlatformBlock getBlock(int x, int y, int z) {
        return new SpigotBlock(getHandle().getBlockAt(x, y, z));
    }

    @Override
    public void setBlock(int x, int y, int z, ResourceKey key) {
        BlockData data = SpigotConversionRegistry.getBlockData(key);
        if (data == null) {
            return;
        }
        helper.setBlock(getHandle(), x, y, z, data);
    }

    @Override
    public IPlatformEntity spawn(double x, double y, double z, ResourceKey key) {
        EntityType type = SpigotConversionRegistry.getEntity(key);
        if (type == null) {
            return null;
        }
        return SpigotConversionRegistry.fromBukkit(getHandle().spawnEntity(new Location(null, x, y, z), type));
    }

    @Override
    public IPlatformArmorStand spawnArmorStand(double x, double y, double z) {
        return new SpigotArmorStand(getHandle().spawn(new Location(null, x, y, z), ArmorStand.class));
    }

    @Override
    public IPlatformFallingBlock spawnFallingBlock(double x, double y, double z, ResourceKey resource) {
        return new SpigotFallingBlock(
            getHandle().spawnFallingBlock(new Location(null, x, y, z), Bukkit.createBlockData(resource.toResourceString())));
    }

    @Override
    public IPlatformItemEntity dropItem(double x, double y, double z, IPlatformItem item, boolean natural) {
        Location location = new Location(null, x, y, z);
        if (natural) {
            return new SpigotItemEntity(getHandle().dropItemNaturally(location, (ItemStack) item.getHandle()));
        }
        return new SpigotItemEntity(getHandle().dropItem(location, (ItemStack) item.getHandle()));
    }

}
