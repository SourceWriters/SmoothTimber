package com.syntaxphoenix.spigot.smoothtimber.compatibility.logblock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;

public class LogBlock extends CompatibilityAddon {

    private LogBlockChopListener chopListener;
    private ExecutorService executorService;

    @Override
    public void onEnable(final PluginPackage pluginPackage, final SmoothTimber smoothTimber) throws Exception {
        executorService = Executors.newCachedThreadPool();
        final de.diddiz.LogBlock.LogBlock plugin = JavaPlugin.getPlugin(de.diddiz.LogBlock.LogBlock.class);

        smoothTimber.getServer().getPluginManager().registerEvents(chopListener = new LogBlockChopListener(plugin.getConsumer()),
            smoothTimber);
        Locator.setLocationResolver(new LogBlockResolver(plugin, executorService));
    }

    @Override
    public void onDisable(final SmoothTimber smoothTimber) throws Exception {
        if (chopListener != null) {
            HandlerList.unregisterAll(chopListener);
        }
        if (executorService != null) {
            executorService.shutdownNow().clear();
        }
    }

}