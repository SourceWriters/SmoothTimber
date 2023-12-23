package com.syntaxphoenix.spigot.smoothtimber.compatibility.factionsuuid;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.IncompatiblePluginException;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;

public class FactionsUUID extends CompatibilityAddon {

    private Listener listener;

    @Override
    public void onEnable(final PluginPackage pluginPackage, final SmoothTimber smoothTimber) throws Exception {
        // FactionsUUID 0.6.x+ support
        try {
            Class.forName("com.massivecraft.factions.perms.PermissibleActions");
            Bukkit.getServer().getPluginManager().registerEvents(listener = new FactionsUUIDChopListener(), smoothTimber);
            return;
        } catch (final ClassNotFoundException ignored) {
        }
        if (listener == null) {
            throw new IncompatiblePluginException("Found a Factions plugin not presently supported by SmoothTimber");
        }
    }

    @Override
    public void onDisable(final SmoothTimber smoothTimber) throws Exception {
        if (listener != null) {
            HandlerList.unregisterAll(listener);
        }
    }

}
