package com.syntaxphoenix.spigot.smoothtimber;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChoppedTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerTreeFallEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.reason.DefaultReason;
import com.syntaxphoenix.spigot.smoothtimber.toggle.ToggleStorage;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown.CooldownHelper;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;
import com.syntaxphoenix.syntaxapi.command.CommandManager;
import com.syntaxphoenix.syntaxapi.reflection.ClassCache;

public class SmoothTimber extends JavaPlugin {

    public static final CommandManager COMMANDS = new CommandManager();

    public static ToggleStorage STORAGE;

    public static SmoothTimber get() {
        return JavaPlugin.getPlugin(SmoothTimber.class);
    }

    public static boolean isFolia() { // yoinked from core protect
        try {
            Class.forName("io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler");
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public void onEnable() {
        PluginUtils.setUp(this);
        if (!PluginUtils.CHANGER.isValid()) {
            return;
        }
        STORAGE = new ToggleStorage(this);

        // Load classes
        CooldownHelper.class.getClass();
        ClassCache.class.getClass();
    }

    @Override
    public void onDisable() {
        if (PluginUtils.CHANGER == null || !PluginUtils.CHANGER.isValid()) {
            return;
        }
				if(isFolia()) {
					Bukkit.getServer().getGlobalRegionScheduler().cancelTasks(this);
				} else {
					Bukkit.getScheduler().cancelTasks(this);
				}
				CooldownHelper.COOLDOWN.getTimer().kill();
    }

    public static boolean triggerChopEvent(final Player player, final Location location, final VersionChanger change, final ItemStack tool,
        final ArrayList<Location> woodBlocks, final int limit) {

        final AsyncPlayerChopTreeEvent event = new AsyncPlayerChopTreeEvent(player, location, change, tool, woodBlocks, limit);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            player.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + Message.ABORT_MESSAGE.colored(new String[] {
                "%reason%",
                event.getReason().orElse(DefaultReason.UNKNOWN).getMessage()
            }));
            return true;
        }
        return false;
    }

    public static AsyncPlayerTreeFallEvent buildFallEvent(final Player player, final Location location, final VersionChanger change,
        final ItemStack tool) {
        return new AsyncPlayerTreeFallEvent(player, location, change, tool);
    }

    public static void triggerFallEvent(final AsyncPlayerTreeFallEvent event) {
        Bukkit.getPluginManager().callEvent(event.lock());
    }

    public static void triggerChoppedEvent(final Player player, final Location location, final VersionChanger change, final ItemStack tool,
        final ArrayList<Location> woodBlocks, final int limit) {
        Bukkit.getPluginManager().callEvent(new AsyncPlayerChoppedTreeEvent(player, location, change, tool, woodBlocks, limit));
    }

}
