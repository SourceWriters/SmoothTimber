package net.sourcewriters.smoothtimber.spigot;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.syntaxphoenix.avinity.module.util.DependencyVersion;
import com.syntaxphoenix.avinity.module.util.DependencyVersionParser;
import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlugin;
import net.sourcewriters.smoothtimber.core.util.ILogAssist;
import net.sourcewriters.smoothtimber.core.util.JavaLogger;

public final class SmoothTimberSpigot extends JavaPlugin implements ISmoothTimberPlugin {

    private final DependencyVersion systemVersion;
    private final JavaLogger logger;

    public SmoothTimberSpigot() {
        this.systemVersion = DependencyVersionParser.INSTANCE.analyze(getDescription().getVersion());
        this.logger = new JavaLogger(new ILogAssist() {
            private final CommandSender sender = Bukkit.getConsoleSender();

            @Override
            public void info(String message) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        });
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

    @Override
    public ILogger getSystemLogger() {
        return logger;
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
