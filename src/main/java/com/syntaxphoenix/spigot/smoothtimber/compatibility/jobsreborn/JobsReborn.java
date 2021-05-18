package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Container;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;

public class JobsReborn extends CompatibilityAddon {

    private final Container<JobsRebornConfig> configContainer = Container.of();
    private final Container<JobsRebornFallListener> listenerContainer = Container.of();

    @Override
    public void onEnable(PluginPackage pluginPackage, SmoothTimber smoothTimber) {
        configContainer.replace(new JobsRebornConfig(this, pluginPackage));
        Bukkit.getPluginManager().registerEvents(listenerContainer.replace(new JobsRebornFallListener()).get(), smoothTimber);
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
