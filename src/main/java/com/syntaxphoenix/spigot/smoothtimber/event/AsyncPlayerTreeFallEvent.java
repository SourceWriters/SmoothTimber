package com.syntaxphoenix.spigot.smoothtimber.event;

import java.util.EnumMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.WoodType;

public class AsyncPlayerTreeFallEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final EnumMap<WoodType, Integer> chopped = new EnumMap<>(WoodType.class);

    private final Player player;
    private final Location treeLocation;
    private final VersionChanger versionChange;
    private final ItemStack toolStack;

    private boolean locked = false;
    private int amount = 0;

    public AsyncPlayerTreeFallEvent(final Player player, final Location treeLocation, final VersionChanger version, final ItemStack tool) {
        super(true);
        this.player = player;
        this.treeLocation = treeLocation;
        this.versionChange = version;
        this.toolStack = tool.clone();
    }

    public final boolean add(final WoodType type) {
        if (locked || type == null || !versionChange.isSupported(type)) {
            return false;
        }
        chopped.put(type, chopped.getOrDefault(type, 0) + 1);
        return true;
    }

    public final AsyncPlayerTreeFallEvent lock() {
        if (!locked) {
            locked = true;
            amount = 0;
            for (final Integer current : chopped.values()) {
                amount += current;
            }
        }
        return this;
    }

    public final Player getPlayer() {
        return player;
    }

    public final int getTotalAmount() {
        return amount;
    }

    public final int getAmount(final WoodType type) {
        return chopped.getOrDefault(type, 0);
    }

    public final WoodType[] getTypes() {
        return chopped.keySet().toArray(new WoodType[0]);
    }

    public final ItemStack getToolStack() {
        return toolStack;
    }

    public final Location getTreeLocation() {
        return treeLocation;
    }

    public final VersionChanger getVersionUtil() {
        return versionChange;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}