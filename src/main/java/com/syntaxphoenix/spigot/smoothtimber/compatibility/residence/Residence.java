package com.syntaxphoenix.spigot.smoothtimber.compatibility.residence;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;

public class Residence extends CompatibilityAddon {

    private final ResidenceChopListener listener;

    public Residence() {
        this.listener = new ResidenceChopListener();
    }

    @Override
    public void onEnable(final PluginPackage pluginPackage, final SmoothTimber smoothTimber) {
        Bukkit.getPluginManager().registerEvents(listener, smoothTimber);
    }

    @Override
    public void onDisable(final SmoothTimber smoothTimber) {
        HandlerList.unregisterAll(listener);
    }

}
