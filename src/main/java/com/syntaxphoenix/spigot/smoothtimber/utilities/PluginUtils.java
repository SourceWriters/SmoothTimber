package com.syntaxphoenix.spigot.smoothtimber.utilities;

import static com.syntaxphoenix.spigot.smoothtimber.SmoothTimber.COMMANDS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.command.CommandRedirect;
import com.syntaxphoenix.spigot.smoothtimber.command.commands.HelpCommand;
import com.syntaxphoenix.spigot.smoothtimber.command.commands.ToggleCommand;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityHandler;
import com.syntaxphoenix.spigot.smoothtimber.config.ConfigTimer;
import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.config.config.AddonConfig;
import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.config.config.MessageConfig;
import com.syntaxphoenix.spigot.smoothtimber.listener.BlockBreakListener;
import com.syntaxphoenix.spigot.smoothtimber.listener.BlockFallListener;
import com.syntaxphoenix.spigot.smoothtimber.listener.PluginLoadListener;
import com.syntaxphoenix.spigot.smoothtimber.stats.SyntaxPhoenixStats;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginSettings;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionExchanger;
import com.syntaxphoenix.syntaxapi.utils.java.Arrays;
import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;

import org.bukkit.ChatColor;

public class PluginUtils {

    public static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();
    public static final PluginSettings SETTINGS = new PluginSettings();

    public static PluginUtils UTILS;
    public static SmoothTimber MAIN;
    public static VersionChanger CHANGER;
    public static SyntaxPhoenixStats STATS;

    public static void setUp(SmoothTimber main) {
        MAIN = main;
        UTILS = new PluginUtils();
    }

    public PluginUtils() {
        MessageConfig.INSTANCE.reload();
        CHANGER = VersionExchanger.getVersionChanger(VersionExchanger.getMinecraftVersion());
        if (!CHANGER.isValid()) {
            return;
        }
        CompatibilityHandler.registerDefaults();
        CutterConfig.INSTANCE.reload();
        registerCommands();
        registerListener();
        registerTasks();
        checkPlugins();
        STATS = new SyntaxPhoenixStats("7vTfe4hf", MAIN);
    }

    /*
     * 
     */

    private void checkPlugins() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        for (Plugin plugin : pluginManager.getPlugins()) {
            if (plugin == null) {
                continue;
            }
            SETTINGS.updatePlugin(plugin, plugin.isEnabled());
        }
        AddonConfig.INSTANCE.reload();
        CompatibilityHandler.handleSettingsUpdate(SETTINGS);
    }

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new BlockBreakListener(), MAIN);
        pluginManager.registerEvents(new BlockFallListener(), MAIN);
        pluginManager.registerEvents(new PluginLoadListener(), MAIN);
    }

    private void registerCommands() {
        CommandRedirect command = new CommandRedirect();

        PluginCommand plugin = MAIN.getCommand("smoothtimber");
        plugin.setExecutor(command);
        plugin.setTabCompleter(command);

        /*
         * 
         */

        COMMANDS.register(new HelpCommand(), "help", "?");
        COMMANDS.register(new ToggleCommand(), "toggle");

    }

    private void registerTasks() {

        SCHEDULER.runTaskTimerAsynchronously(MAIN, ConfigTimer.TIMER, 20, 60);

    }

    /*
     * Task Util
     */

    public static <E> E getObjectFromMainThread(Supplier<E> supply) {
        return getObjectFromMainThread(supply, CutterConfig.GLOBAL_SYNC_TIME);
    }

    public static <E> E getObjectFromMainThread(Supplier<E> supply, long wait) {
        CountDownLatch latch = new CountDownLatch(1);
        StoredObject<E> value = new StoredObject<>();
        SCHEDULER.runTask(MAIN, () -> {
            value.setObject(supply.get());
            latch.countDown();
        });
        try {
            latch.await(wait, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Thread interrupted", e);
        }
        return value.getObject();
    }

    /*
     * Console Util
     */

    public static void sendConsoleMessage(boolean prefix, String message) {
        Bukkit.getConsoleSender()
            .sendMessage(ChatColor.translateAlternateColorCodes('&', prefix ? Message.GLOBAL_PREFIX.message() + ' ' + message : message));
    }

    public static void sendConsoleMessage(boolean prefix, String... messages) {
        for (String message : messages) {
            sendConsoleMessage(prefix, message);
        }
    }

    public static void sendConsoleMessageMultiline(boolean prefix, String message) {
        sendConsoleMessage(prefix, message.split("\n"));
    }

    public static void sendConsoleError(String reason, Throwable throwable) {
        PluginUtils.sendConsoleMessage(false, Arrays.merge(String[]::new, Arrays.merge(String[]::new, Centering.center(new String[] {
            "&8##################################################################",
            "&r",
            "&8<=> &5Smooth&dTimber &8<=>",
            "&7" + reason,
            "&r",
            "&8##################################################################"
        }), Exceptions.stackTraceToStringArray(throwable)), "&8##################################################################"));
    }

}
