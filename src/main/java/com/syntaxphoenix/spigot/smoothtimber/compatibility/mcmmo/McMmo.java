package com.syntaxphoenix.spigot.smoothtimber.compatibility.mcmmo;

import org.bukkit.event.HandlerList;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;

public class McMmo extends CompatibilityAddon {

    McMmoChopListener chopListener;

    @Override
    public void onEnable(final PluginPackage pluginPackage, final SmoothTimber smoothTimber) throws Exception {
        smoothTimber.getServer().getPluginManager().registerEvents(chopListener = new McMmoChopListener(), smoothTimber);
    }

    @Override
    public void onDisable(final SmoothTimber smoothTimber) throws Exception {
        if (chopListener != null) {
            HandlerList.unregisterAll(chopListener);
        }
    }

}