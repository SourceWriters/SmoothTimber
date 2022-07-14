package net.sourcewriters.smoothtimber.spigot.world;

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

    private final World world;
    private final VersionHelper helper = VersionHelper.get();

    public SpigotWorld(final World world) {
        this.world = world;
    }

    @Override
    public World getHandle() {
        return world;
    }

    @Override
    public IPlatformPlayer[] getPlayers() {
        Player[] players = world.getPlayers().toArray(Player[]::new);
        IPlatformPlayer[] platform = new IPlatformPlayer[players.length];
        for (int index = 0; index < players.length; index++) {
            platform[index] = new SpigotPlayer(players[index]);
        }
        return platform;
    }

    @Override
    public IPlatformBlock getBlock(int x, int y, int z) {
        return new SpigotBlock(world.getBlockAt(x, y, z));
    }

    @Override
    public void setBlock(int x, int y, int z, ResourceKey key) {
        BlockData data = SpigotConversionRegistry.getBlockData(key);
        if (data == null) {
            return;
        }
        helper.setBlock(world, x, y, z, data);
    }

    @Override
    public IPlatformEntity spawn(double x, double y, double z, ResourceKey key) {
        EntityType type = SpigotConversionRegistry.getEntity(key);
        if (type == null) {
            return null;
        }
        return SpigotConversionRegistry.fromBukkit(world.spawnEntity(new Location(null, x, y, z), type));
    }

    @Override
    public IPlatformArmorStand spawnArmorStand(double x, double y, double z) {
        return new SpigotArmorStand(world.spawn(new Location(null, x, y, z), ArmorStand.class));
    }

    @Override
    public IPlatformFallingBlock spawnFallingBlock(double x, double y, double z, ResourceKey resource) {
        return new SpigotFallingBlock(
            world.spawnFallingBlock(new Location(null, x, y, z), Bukkit.createBlockData(resource.toResourceString())));
    }

    @Override
    public IPlatformItemEntity dropItem(double x, double y, double z, IPlatformItem item, boolean natural) {
        Location location = new Location(null, x, y, z);
        if (natural) {
            return new SpigotItemEntity(world.dropItemNaturally(location, (ItemStack) item.getHandle()));
        }
        return new SpigotItemEntity(world.dropItem(location, (ItemStack) item.getHandle()));
    }

}
