package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Container;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;
import com.syntaxphoenix.syntaxapi.version.Version;

public class JobsReborn extends CompatibilityAddon {

    private final Container<JobsRebornConfig> configContainer = Container.of();
    private final Container<Listener> listenerContainer = Container.of();

    @Override
    public void onEnable(PluginPackage pluginPackage, SmoothTimber smoothTimber) {
        configContainer.replace(new JobsRebornConfig(this, pluginPackage));
        Bukkit.getPluginManager().registerEvents(listenerContainer.replace(getListener(pluginPackage.getVersion())).get(), smoothTimber);
    }

    private Listener getListener(Version version) {
        if(version.getMajor() > 4 || (version.getMajor() == 4 && version.getMinor() > 16)) {
            return new JobsRebornFallListenerNew();
        }
        return new JobsRebornFallListenerLegacy();
    }

    @Override
    public void onDisable(SmoothTimber smoothTimber) {
        if (listenerContainer.isPresent()) {
            HandlerList.unregisterAll(listenerContainer.get());
            listenerContainer.replace(null);
        }
        configContainer.replace(null);
    }

    @Override
    public JobsRebornConfig getConfig() {
        return configContainer.get();
    }

}
