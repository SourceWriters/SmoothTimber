package com.syntaxphoenix.spigot.smoothtimber.compatibility.lands;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;

import me.angeschossen.lands.api.integration.LandsIntegration;

public class Lands extends CompatibilityAddon {

    private final LandsChopListener listener;
    private LandsIntegration integration;

    public Lands() {
        this.listener = new LandsChopListener(this);
    }

    @Override
    public void onEnable(PluginPackage pluginPackage, SmoothTimber smoothTimber) {
        Bukkit.getPluginManager().registerEvents(listener, smoothTimber);
        integration = new LandsIntegration(smoothTimber);
    }

    @Override
    public void onDisable(SmoothTimber smoothTimber) {
        HandlerList.unregisterAll(listener);
    }

    public LandsIntegration getIntegration() {
        return integration;

    }

}
