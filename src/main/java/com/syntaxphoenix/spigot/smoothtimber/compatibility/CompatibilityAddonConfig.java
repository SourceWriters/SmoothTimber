package com.syntaxphoenix.spigot.smoothtimber.compatibility;

import java.io.File;

import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.config.Migration;
import com.syntaxphoenix.spigot.smoothtimber.config.STConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;

public abstract class CompatibilityAddonConfig<A extends CompatibilityAddon> extends STConfig {

    private final A addon;
    private final String name;

    public CompatibilityAddonConfig(A addon, PluginPackage pluginPackage, Class<? extends Migration> clazz, int latestVersion) {
        super(new File("plugins/SmoothTimber/addons", pluginPackage.getName() + ".yml"), clazz, latestVersion);
        this.addon = addon;
        this.name = pluginPackage.getName();
    }

    public A getAddon() {
        return addon;
    }

    public final String getAddonName() {
        return name;
    }

    @Override
    protected final String getSingleType() {
        return Message.TYPE_ADDON.message(new String[] {
            "%addon%",
            name
        });
    }

    @Override
    protected final String getMultipleType() {
        return Message.TYPE_ADDONS.message(new String[] {
            "%addon%",
            name
        });
    }

}
