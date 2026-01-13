package com.syntaxphoenix.spigot.smoothtimber.compatibility.uxmclaims;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;

public class UxmClaims extends CompatibilityAddon {

    private final UxmClaimsChopListener listener = new UxmClaimsChopListener();

    @Override
    public void onEnable(PluginPackage pluginPackage, SmoothTimber smoothTimber) {
        Bukkit.getPluginManager().registerEvents(listener, smoothTimber);
    }

    @Override
    public void onDisable(SmoothTimber smoothTimber) {
        HandlerList.unregisterAll(listener);
    }
}
