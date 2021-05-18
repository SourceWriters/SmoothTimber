package com.syntaxphoenix.spigot.smoothtimber.compatibility;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;

public abstract class CompatibilityAddon {

    public abstract void onEnable(PluginPackage pluginPackage, SmoothTimber smoothTimber) throws Exception;

    public abstract void onDisable(SmoothTimber smoothTimber) throws Exception;

    public CompatibilityAddonConfig<?> getConfig() {
        return null;
    }

    public boolean hasConfig() {
        return getConfig() != null;
    }

}
