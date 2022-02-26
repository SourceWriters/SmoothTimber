package net.sourcewriters.smoothtimber.spigot;

import java.io.File;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.syntaxphoenix.avinity.module.util.DependencyVersion;
import com.syntaxphoenix.avinity.module.util.DependencyVersionParser;

import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlugin;
import net.sourcewriters.smoothtimber.core.SmoothTimberCore;

public final class SmoothTimberSpigot extends JavaPlugin implements ISmoothTimberPlugin {

    private final DependencyVersion systemVersion;
    private final SmoothTimberCore core;

    public SmoothTimberSpigot() {
        this.systemVersion = DependencyVersionParser.INSTANCE.analyze(getDescription().getVersion());
        this.core = new SmoothTimberCore(new Consumer<String>() {
            private final CommandSender sender = Bukkit.getConsoleSender();

            @Override
            public void accept(final String message) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        }, new SpigotPlatform(this));
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    /*
     * Plug-in implementation
     */

    public SmoothTimberCore getCore() {
        return core;
    }

    @Override
    public DependencyVersion getSystemVersion() {
        return systemVersion;
    }

    @Override
    public File getSystemFolder() {
        return getDataFolder();
    }

    @Override
    public File getSystemJar() {
        return getFile();
    }

}
