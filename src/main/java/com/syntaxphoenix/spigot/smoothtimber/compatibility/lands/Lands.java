package com.syntaxphoenix.spigot.smoothtimber.compatibility.lands;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;

public class Lands extends CompatibilityAddon {

    private Listener listener;

    @Override
    public void onEnable(final PluginPackage pluginPackage, final SmoothTimber smoothTimber) {
        this.listener = createListener(pluginPackage, smoothTimber);
        Bukkit.getPluginManager().registerEvents(listener, smoothTimber);
    }

    @Override
    public void onDisable(final SmoothTimber smoothTimber) {
        if (listener != null) {
            listener = null;
            HandlerList.unregisterAll(listener);
        }
    }
    
    private Listener createListener(PluginPackage pluginPackage, SmoothTimber smoothTimber) {
        try {
            Class.forName("me.angeschossen.lands.api.LandsIntegration");
            return new LandsChopListener_v2(smoothTimber);
        } catch(ClassNotFoundException e) {
            return new LandsChopListener_v1(smoothTimber);
        }
    }

}
