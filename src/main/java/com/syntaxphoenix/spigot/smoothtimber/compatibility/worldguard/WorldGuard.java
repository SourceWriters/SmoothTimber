package com.syntaxphoenix.spigot.smoothtimber.compatibility.worldguard;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionExchanger;

public class WorldGuard extends CompatibilityAddon {

    private Listener listener;

    @Override
    public void onEnable(final PluginPackage pluginPackage, final SmoothTimber smoothTimber) {
        switch (VersionExchanger.getCoreVersion()) {
        case v1_8x:
        case v1_9x:
        case v1_11x:
            listener = new WorldGuardChopListener_v6_x();
            break;
        case v1_13x:
        case v1_16x:
        case v1_19x:
        case v1_20x:
            listener = new WorldGuardChopListener_v7_x();
            break;
        default:
            return;
        }
        Bukkit.getPluginManager().registerEvents(listener, smoothTimber);
    }

    @Override
    public void onDisable(final SmoothTimber smoothTimber) {
        if (listener != null) {
            HandlerList.unregisterAll(listener);
            listener = null;
        }
    }

}
