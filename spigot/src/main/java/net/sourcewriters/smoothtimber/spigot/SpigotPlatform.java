package net.sourcewriters.smoothtimber.spigot;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberExecutor;
import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlatform;
import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlugin;
import net.sourcewriters.smoothtimber.api.platform.event.manager.IPlatformEventAdapter;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformInventory;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.spigot.event.SpigotEventAdapter;
import net.sourcewriters.smoothtimber.spigot.event.adapter.*;
import net.sourcewriters.smoothtimber.spigot.world.SpigotWorld;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotPlayer;
import net.sourcewriters.smoothtimber.spigot.world.inventory.SpigotItem;
import net.sourcewriters.smoothtimber.spigot.world.inventory.SpigotModifiableInventory;

public final class SpigotPlatform implements ISmoothTimberPlatform {

    private final SmoothTimberSpigot plugin;
    private final SpigotExecutor executor;

    private final SpigotEventAdapter eventAdapter;

    public SpigotPlatform(final SmoothTimberSpigot plugin) {
        this.plugin = plugin;
        this.executor = new SpigotExecutor(plugin);
        this.eventAdapter = new SpigotEventAdapter(plugin);
        registerAdapters();
    }

    private void registerAdapters() {
        eventAdapter.register(new EntityChangeBlockAdapter());
        eventAdapter.register(new BlockBreakAdapter());
    }

    @Override
    public ISmoothTimberPlugin getPlugin() {
        return plugin;
    }

    @Override
    public ISmoothTimberExecutor getExecutor() {
        return executor;
    }

    @Override
    public IPlatformEventAdapter getEventAdapter() {
        return eventAdapter;
    }

    @Override
    public boolean isValidMaterial(ResourceKey key) {
        return SpigotConversionRegistry.getMaterial(key) != null;
    }

    @Override
    public IPlatformPlayer[] getPlayers() {
        Player[] players = Bukkit.getOnlinePlayers().toArray(Player[]::new);
        IPlatformPlayer[] platform = new IPlatformPlayer[players.length];
        for (int index = 0; index < players.length; index++) {
            platform[index] = new SpigotPlayer(players[index]);
        }
        return platform;
    }

    @Override
    public IPlatformPlayer getPlayer(UUID uniqueId) {
        Player player = Bukkit.getPlayer(uniqueId);
        if (player == null) {
            return null;
        }
        return new SpigotPlayer(player);
    }

    @Override
    public int getWorldAmount() {
        return Bukkit.getWorlds().size();
    }

    @Override
    public IPlatformWorld[] getWorlds() {
        return Bukkit.getWorlds().stream().map(SpigotWorld::new).toArray(IPlatformWorld[]::new);
    }

    @Override
    public IPlatformWorld getWorldByIndex(final int index) {
        List<World> list = Bukkit.getWorlds();
        if (list.size() <= index || index < 0) {
            return null;
        }
        return new SpigotWorld(list.get(index));
    }

    @Override
    public IPlatformWorld getWorldById(final UUID uniqueId) {
        World world = Bukkit.getWorld(uniqueId);
        if (world == null) {
            return null;
        }
        return new SpigotWorld(world);
    }

    @Override
    public IPlatformWorld getWorldByName(final String name) {
        World world = Bukkit.getWorld(name);
        if (world == null) {
            return null;
        }
        return new SpigotWorld(world);
    }

    @Override
    public IPlatformItem buildItem(final ResourceKey key) {
        Material material = SpigotConversionRegistry.getMaterial(key);
        if (material == null) {
            return null;
        }
        return SpigotItem.of(new ItemStack(material));
    }

    @Override
    public IPlatformInventory buildInventory() {
        return new SpigotModifiableInventory();
    }

}
