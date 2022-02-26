package net.sourcewriters.smoothtimber.spigot;

import java.util.UUID;

import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberExecutor;
import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlatform;
import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlugin;
import net.sourcewriters.smoothtimber.api.platform.event.manager.IPlatformEventAdapter;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformInventory;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public final class SpigotPlatform implements ISmoothTimberPlatform {

    private final SmoothTimberSpigot plugin;

    public SpigotPlatform(final SmoothTimberSpigot plugin) {
        this.plugin = plugin;
    }

    @Override
    public ISmoothTimberPlugin getPlugin() {
        return plugin;
    }

    @Override
    public ISmoothTimberExecutor getExecutor() {
        return null;
    }

    @Override
    public IPlatformEventAdapter getEventAdapter() {
        return null;
    }

    @Override
    public IPlatformPlayer[] getPlayers() {
        return null;
    }

    @Override
    public int getWorldAmount() {
        return 0;
    }

    @Override
    public IPlatformWorld getWorlds() {
        return null;
    }

    @Override
    public IPlatformWorld getWorldByIndex(final int index) {
        return null;
    }

    @Override
    public IPlatformWorld getWorldById(final UUID uniqueId) {
        return null;
    }

    @Override
    public IPlatformWorld getWorldByName(final String name) {
        return null;
    }

    @Override
    public IPlatformItem buildItem(final ResourceKey key) {
        return null;
    }

    @Override
    public IPlatformInventory buildInventory() {
        return null;
    }

}
