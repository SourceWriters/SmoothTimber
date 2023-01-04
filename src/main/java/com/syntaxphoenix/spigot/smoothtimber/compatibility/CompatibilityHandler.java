package com.syntaxphoenix.spigot.smoothtimber.compatibility;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.plugin.java.JavaPlugin;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.blockylog.BlockyLog;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.coreprotect.CoreProtect;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.griefprevention.GriefPrevention;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.JobsReborn;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.lands.Lands;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.logblock.LogBlock;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.mcmmo.McMmo;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.placeholderapi.PlaceholderApi;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.residence.Residence;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.towny.Towny;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.worldguard.WorldGuard;
import com.syntaxphoenix.spigot.smoothtimber.config.config.AddonConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginSettings;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;
import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;

public abstract class CompatibilityHandler {

    private static final Map<String, CompatAddon<?>> COMPAT = Collections.synchronizedMap(new HashMap<>());

    public static void registerDefaults() {
        register("CoreProtect", CoreProtect.class);
        register("PlaceholderAPI", PlaceholderApi.class);
        register("GriefPrevention", GriefPrevention.class);
        register("WorldGuard", WorldGuard.class);
        register("Jobs", JobsReborn.class);
        register("Residence", Residence.class);
        register("BlockyLog", BlockyLog.class);
        register("Lands", Lands.class);
        register("LogBlock", LogBlock.class);
        register("mcMMO", McMmo.class);
        register("Towny", Towny.class);
    }

    public static <E extends CompatibilityAddon> boolean register(final String pluginName, final Class<E> addonClass) {
        if (COMPAT.containsKey(pluginName) || isAddonRegistered(addonClass)) {
            return false;
        }
        COMPAT.put(pluginName, new CompatAddon<>(addonClass));
        return true;
    }

    public static String[] getCompatibilityNames() {
        return COMPAT.keySet().toArray(new String[0]);
    }

    public static <E extends CompatibilityAddon> boolean isAddonRegistered(final Class<E> addonClass) {
        return COMPAT.values().stream().unordered().anyMatch(addon -> addon.getOwner().equals(addonClass));
    }

    @SuppressWarnings("unchecked")
    public static <E extends CompatibilityAddon> Optional<E> getAddon(final Class<E> addonClass) {
        return COMPAT.values().stream().unordered().filter(addon -> addon.getOwner().equals(addonClass)).findFirst()
            .flatMap(CompatAddon::getInstance).map(addon -> (E) addon);
    }

    public static Optional<CompatibilityAddon> getAddon(final String pluginName) {
        return Optional.ofNullable(COMPAT.get(pluginName)).flatMap(CompatAddon::getInstance);
    }

    public static void handleSettingsUpdate(final PluginSettings settings) {
        Optional<PluginPackage> optional;
        for (final String name : COMPAT.keySet()) {
            if (!(optional = settings.searchPackage(name)).isPresent()) {
                final CompatAddon<?> addon = COMPAT.get(name);
                if (addon.getInstance().isPresent()) {
                    addon.shutdown();
                }
                continue;
            }
            final CompatAddon<?> addon = COMPAT.get(name);
            final PluginPackage pluginPackage = optional.get();
            if (pluginPackage.getPlugin().isEnabled()) {
                final boolean disabled = AddonConfig.INSTANCE.isDisabled(pluginPackage.getName());
                if (!addon.getInstance().isPresent() && !disabled) {
                    addon.start(pluginPackage);
                } else if (disabled) {
                    addon.shutdown();
                }
                continue;
            }
            if (addon.getInstance().isPresent()) {
                addon.shutdown();
            }
        }
    }

    public static class CompatAddon<E extends CompatibilityAddon> {

        private final Class<E> owner;
        private final Reflect reflect;
        
        private final String name;

        private CompatibilityAddon instance;

        private CompatAddon(final Class<E> owner) {
            this.owner = owner;
            this.name = owner.getSimpleName().split("\\.")[0];
            this.reflect = new Reflect(owner);
        }

        public Class<E> getOwner() {
            return owner;
        }

        public Reflect getReflect() {
            return reflect;
        }

        public Optional<CompatibilityAddon> getInstance() {
            return Optional.ofNullable(instance);
        }

        void start(final PluginPackage pluginPackage) {
            if (instance != null) {
                return;
            }
            instance = (CompatibilityAddon) reflect.init();
            try {
                PluginUtils.sendConsoleMessage(true, "&7Trying to enable compatibility addon '&3" + name + "&7' for plugin '&3" + pluginPackage.getName() + "&7'...");
                instance.onEnable(pluginPackage, JavaPlugin.getPlugin(SmoothTimber.class));
                if (instance.hasConfig()) {
                    PluginUtils.sendConsoleMessage(true, "&7Trying to load config of compatibility addon '&3" + name + "&7' for plugin '&3" + pluginPackage.getName() + "&7'...");
                    instance.getConfig().reload();
                }
                PluginUtils.sendConsoleMessage(true, "&7Successfully enabled compatbility addon '&3" + name + "&7' for plugin '&3" + pluginPackage.getName() + "&7'...");
            } catch (final IncompatiblePluginException pluginException) {
                instance = null;
                PluginUtils.sendConsoleMessage(true, Exceptions.stackTraceToString(pluginException));
            } catch (final Exception exception) {
                instance = null;
                PluginUtils.sendConsoleMessage(true, "&7Failed to enable compatibility addon '&3" + name
                    + "&7' for plugin '&3" + pluginPackage.getName() + "&7'!");
            }
        }

        void shutdown() {
            if (instance == null) {
                return;
            }
            try {
                if (instance.hasConfig()) {
                    instance.getConfig().unload();
                }
                instance.onDisable(JavaPlugin.getPlugin(SmoothTimber.class));
            } catch (final Exception exception) {
                PluginUtils.sendConsoleMessage(true,
                    "&bFailed to disable compatibility addon " + owner.getSimpleName().split("\\.")[0] + "&7'!");
            }
            instance = null;
        }

    }

}
