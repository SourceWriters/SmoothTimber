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

public class SmoothTimber extends JavaPlugin {

    public static final CommandManager COMMANDS = new CommandManager();

    public static ToggleStorage STORAGE;

    public static SmoothTimber get() {
        return JavaPlugin.getPlugin(SmoothTimber.class);
    }

    public void onEnable() {
        PluginUtils.setUp(this);
        if (PluginUtils.CHANGER != null) {
            STORAGE = new ToggleStorage(this);
        }
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        CooldownHelper.COOLDOWN.getTimer().kill();
    }

    public static boolean triggerChopEvent(Player player, Location location, VersionChanger change, ItemStack tool,
        ArrayList<Location> woodBlocks, int limit) {

        AsyncPlayerChopTreeEvent event = new AsyncPlayerChopTreeEvent(player, location, change, tool, woodBlocks, limit);
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

    public static AsyncPlayerTreeFallEvent buildFallEvent(Player player, Location location, VersionChanger change, ItemStack tool) {
        return new AsyncPlayerTreeFallEvent(player, location, change, tool);
    }

    public static void triggerFallEvent(AsyncPlayerTreeFallEvent event) {
        Bukkit.getPluginManager().callEvent(event.lock());
    }

    public static void triggerChoppedEvent(Player player, Location location, VersionChanger change, ItemStack tool,
        ArrayList<Location> woodBlocks, int limit) {
        Bukkit.getPluginManager().callEvent(new AsyncPlayerChoppedTreeEvent(player, location, change, tool, woodBlocks, limit));
    }

}
