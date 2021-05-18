package com.syntaxphoenix.spigot.smoothtimber.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityHandler;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginSettings;

public class PluginLoadListener implements Listener {

    private final PluginSettings settings = PluginUtils.SETTINGS;

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEnable(PluginEnableEvent event) {
        PluginUtils.SETTINGS.updatePlugin(event.getPlugin(), true);
        CompatibilityHandler.handleSettingsUpdate(settings);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onDisable(PluginDisableEvent event) {
        PluginUtils.SETTINGS.updatePlugin(event.getPlugin(), false);
        CompatibilityHandler.handleSettingsUpdate(settings);
    }

}
