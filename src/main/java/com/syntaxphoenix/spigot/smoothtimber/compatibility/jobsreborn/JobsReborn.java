package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.IncompatiblePluginException;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.adapter.AdapterLegacy4_16;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.adapter.AdapterLegacy4_17;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Container;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;
import com.syntaxphoenix.spigot.smoothtimber.version.Version;

public class JobsReborn extends CompatibilityAddon {

    private final Container<JobsRebornConfig> configContainer = Container.of();
    private final Container<JobsRebornFallListener> listenerContainer = Container.of();

    @Override
    public void onEnable(final PluginPackage pluginPackage, final SmoothTimber smoothTimber) {
        final JobAdapter adapter = getAdapter(pluginPackage);
        if (adapter == null) {
            throw new IncompatiblePluginException("Compatibility for " + pluginPackage.getName()
                + " is unable to load JobAdapter for version " + pluginPackage.getVersionRaw());
        }
        configContainer.replace(new JobsRebornConfig(this, pluginPackage));
        Bukkit.getPluginManager().registerEvents(listenerContainer.replace(new JobsRebornFallListener(adapter)).get(), smoothTimber);
    }

    private JobAdapter getAdapter(final PluginPackage pluginPackage) {
        final Version version = pluginPackage.getVersion();
        if (version.major() < 4 || version.major() == 4 && version.minor() <= 16) {
            return new AdapterLegacy4_16();
        }
        return new AdapterLegacy4_17();
    }

    @Override
    public void onDisable(final SmoothTimber smoothTimber) {
        if (listenerContainer.isPresent()) {
            HandlerList.unregisterAll(listenerContainer.get());
            listenerContainer.get().close();
            listenerContainer.replace(null);
        }
        configContainer.replace(null);
    }

    @Override
    public JobsRebornConfig getConfig() {
        return configContainer.get();
    }

}
