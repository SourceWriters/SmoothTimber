package com.syntaxphoenix.spigot.smoothtimber.compatibility.placeholderapi;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;

public class PlaceholderApi extends CompatibilityAddon {

    private final SmoothTimberPlaceholders placeholders = new SmoothTimberPlaceholders();

    @Override
    public void onEnable(PluginPackage pluginPackage, SmoothTimber smoothTimber) throws Exception {
        placeholders.enable(smoothTimber.getDescription());
    }

    @Override
    public void onDisable(SmoothTimber smoothTimber) throws Exception {
        placeholders.disable();
    }

}
