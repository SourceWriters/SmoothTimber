package com.syntaxphoenix.spigot.smoothtimber.utilities.plugin;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.reflection.ReflectCache;
import com.syntaxphoenix.syntaxapi.version.DefaultVersion;
import com.syntaxphoenix.syntaxapi.version.Version;
import com.syntaxphoenix.syntaxapi.version.VersionAnalyzer;

public class PluginPackage {

    public static final VersionAnalyzer ANALYZER = new DefaultVersion().getAnalyzer();

    private ReflectCache cache = new ReflectCache();

    private Version version;
    private String versionRaw;
    private String name;
    private Plugin plugin;

    PluginPackage(final Plugin plugin) {
        update(plugin);
    }

    /*
     * 
     */

    final void delete() {
        version = null;
        plugin = null;
        name = null;
        cache.clear();
        cache = null;
    }

    final void update(final Plugin plugin) {
        this.plugin = plugin;
        this.name = plugin.getName();
        this.versionRaw = plugin.getDescription().getVersion();
        this.version = ANALYZER.analyze(versionRaw.contains("[") ? versionRaw.split("\\[")[0] : versionRaw);
    }

    /*
     * 
     */

    public ReflectCache getCache() {
        return cache;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getVersionRaw() {
        return versionRaw;
    }

    public Version getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    /*
     * 
     */

    public boolean isFromPlugin(final Plugin plugin) {
        return hasName(plugin.getName());
    }

    public boolean hasName(final String name) {
        return this.name.equals(name);
    }

}
