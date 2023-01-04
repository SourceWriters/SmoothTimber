package com.syntaxphoenix.spigot.smoothtimber.utilities.plugin;

import java.util.ArrayList;
import java.util.Optional;

import org.bukkit.plugin.Plugin;

public class PluginSettings {

    private final ArrayList<PluginPackage> packages = new ArrayList<>();

    public void updatePlugin(final Plugin plugin, final boolean enabled) {
        final Optional<PluginPackage> option = searchPackage(plugin);
        if (enabled) {
            if (option.isPresent()) {
                option.get().update(plugin);
            } else {
                packages.add(new PluginPackage(plugin));
            }
            return;
        }
        if (!option.isPresent()) {
            return;
        }
        final PluginPackage pack = option.get();
        packages.remove(pack);
        pack.delete();
    }

    public Optional<PluginPackage> searchPackage(final Plugin plugin) {
        return packages.stream().filter(pack -> pack.isFromPlugin(plugin)).findFirst();
    }

    public Optional<PluginPackage> searchPackage(final String name) {
        return packages.stream().filter(pack -> pack.hasName(name)).findFirst();
    }

    public PluginPackage getPackage(final Plugin plugin) {
        if (packages.isEmpty()) {
            return null;
        }
        final Optional<PluginPackage> option = searchPackage(plugin);
        return option.isPresent() ? option.get() : null;
    }

    public PluginPackage getPackage(final String name) {
        if (packages.isEmpty()) {
            return null;
        }
        final Optional<PluginPackage> option = searchPackage(name);
        return option.isPresent() ? option.get() : null;
    }

}
