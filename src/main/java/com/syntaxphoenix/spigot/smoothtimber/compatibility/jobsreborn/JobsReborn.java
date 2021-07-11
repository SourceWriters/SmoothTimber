package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.gamingmesh.jobs.container.Job;
import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Container;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;
import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public class JobsReborn extends CompatibilityAddon {

    private final Container<JobsRebornConfig> configContainer = Container.of();
    private final Container<Listener> listenerContainer = Container.of();

    @Override
    public void onEnable(PluginPackage pluginPackage, SmoothTimber smoothTimber) {
        configContainer.replace(new JobsRebornConfig(this, pluginPackage));
        Bukkit.getPluginManager().registerEvents(listenerContainer.replace(getListener()).get(), smoothTimber);
    }

    private Listener getListener() {
        AbstractReflect reflect = new Reflect(Job.class).searchMethod("name", "getName").searchMethod("test", "getJobKeyName");
        if (reflect.getMethod("test") != null) {
            reflect.delete();
            return new JobsRebornFallListenerLegacy();
        }
        return new JobsRebornFallListenerNew(reflect);
    }

    @Override
    public void onDisable(SmoothTimber smoothTimber) {
        if (listenerContainer.isPresent()) {
            HandlerList.unregisterAll(listenerContainer.get());
            listenerContainer.replace(null);
        }
        configContainer.replace(null);
    }

    @Override
    public JobsRebornConfig getConfig() {
        return configContainer.get();
    }

}
