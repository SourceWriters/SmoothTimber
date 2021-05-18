package com.syntaxphoenix.spigot.smoothtimber.compatibility;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.syntaxphoenix.spigot.smoothtimber.compatibility.towny.Towny;
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
import com.syntaxphoenix.spigot.smoothtimber.compatibility.worldguard.WorldGuard;
import com.syntaxphoenix.spigot.smoothtimber.config.config.AddonConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginSettings;
import com.syntaxphoenix.syntaxapi.reflections.Reflect;
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

    public static <E extends CompatibilityAddon> boolean register(String pluginName, Class<E> addonClass) {
        if (COMPAT.containsKey(pluginName) || isAddonRegistered(addonClass)) {
            return false;
        }
        COMPAT.put(pluginName, new CompatAddon<E>(addonClass));
        return true;
    }

    public static String[] getCompatibilityNames() {
        return COMPAT.keySet().toArray(new String[0]);
    }

    public static <E extends CompatibilityAddon> boolean isAddonRegistered(Class<E> addonClass) {
        return COMPAT.values().stream().unordered().anyMatch(addon -> addon.getOwner().equals(addonClass));
    }

    @SuppressWarnings("unchecked")
    public static <E extends CompatibilityAddon> Optional<E> getAddon(Class<E> addonClass) {
        return COMPAT.values().stream().unordered().filter(addon -> addon.getOwner().equals(addonClass)).findFirst()
            .flatMap(CompatAddon::getInstance).map(addon -> (E) addon);
    }

    public static Optional<CompatibilityAddon> getAddon(String pluginName) {
        return Optional.ofNullable(COMPAT.get(pluginName)).flatMap(CompatAddon::getInstance);
    }

    public static void handleSettingsUpdate(PluginSettings settings) {
        Optional<PluginPackage> optional;
        for (String name : COMPAT.keySet()) {
            if (!(optional = settings.searchPackage(name)).isPresent()) {
                CompatAddon<?> addon = COMPAT.get(name);
                if (addon.getInstance().isPresent()) {
                    addon.shutdown();
                }
                continue;
            }
            CompatAddon<?> addon = COMPAT.get(name);
            PluginPackage pluginPackage = optional.get();
            if (pluginPackage.getPlugin().isEnabled()) {
                boolean disabled = AddonConfig.INSTANCE.isDisabled(pluginPackage.getName());
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

        private CompatibilityAddon instance;

        private CompatAddon(Class<E> owner) {
            this.owner = owner;
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

        void start(PluginPackage pluginPackage) {
            if (instance != null) {
                return;
            }
            instance = (CompatibilityAddon) reflect.init();
            try {
                instance.onEnable(pluginPackage, JavaPlugin.getPlugin(SmoothTimber.class));
                if (instance.hasConfig()) {
                    instance.getConfig().reload();
                }
            } catch (IncompatiblePluginException pluginException) {
                instance = null;
                PluginUtils.sendConsoleMessage(true, Exceptions.stackTraceToString(pluginException));
            } catch (Exception exception) {
                instance = null;
                PluginUtils.sendConsoleMessage(true, "&bFailed to enable compatibility addon '&3" + owner.getSimpleName().split("\\.")[0]
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
            } catch (Exception exception) {
                PluginUtils.sendConsoleMessage(true,
                    "&bFailed to disable compatibility addon " + owner.getSimpleName().split("\\.")[0] + "&7'!");
            }
            instance = null;
        }

    }

}
