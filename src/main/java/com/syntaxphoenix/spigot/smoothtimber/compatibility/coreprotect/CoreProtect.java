package com.syntaxphoenix.spigot.smoothtimber.compatibility.coreprotect;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.LocationResolver;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionExchanger;

public class CoreProtect extends CompatibilityAddon {

    private LocationResolver resolver;
    private Listener chopListener;
    private Listener growListener;
    private CoreCompat compat;

    private ExecutorService service;

    @Override
    public void onEnable(PluginPackage pluginPackage, SmoothTimber smoothTimber) throws Exception {
        if (resolver != null) {
            return;
        }
        resolver = new CoreProtectResolver(service = Executors.newCachedThreadPool(), pluginPackage.getPlugin());
        Locator.setLocationResolver(resolver);
        switch (VersionExchanger.getCoreVersion()) {
        case v1_8x:
        case v1_9x:
        case v1_11x:
            compat = new CoreCompat_v1_8_x(pluginPackage.getPlugin(), PluginUtils.CHANGER);
            break;
        case v1_13x:
        case v1_16x:
        case v1_19x:
            compat = new CoreCompat_v1_13_x(pluginPackage.getPlugin());
            break;
        default:
            return;
        }
        Bukkit.getPluginManager().registerEvents(chopListener = new CoreProtectChopListener(compat), smoothTimber);
        Bukkit.getPluginManager().registerEvents(growListener = new CoreProtectGrowListener(compat), smoothTimber);
    }

    @Override
    public void onDisable(SmoothTimber smoothTimber) throws Exception {
        if (resolver != null) {
            resolver = null;
            Locator.setLocationResolver(null);
        }
        if (chopListener != null) {
            HandlerList.unregisterAll(chopListener);
            chopListener = null;
        }
        if (growListener != null) {
            HandlerList.unregisterAll(growListener);
            growListener = null;
        }
        if (service != null) {
            service.shutdownNow().clear();
        }
    }

    public final LocationResolver getLocationResolver() {
        return resolver;
    }

}