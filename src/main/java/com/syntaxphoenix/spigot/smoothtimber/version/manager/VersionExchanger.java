package com.syntaxphoenix.spigot.smoothtimber.version.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.version.changer.v1_11xChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.changer.v1_13xChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.changer.v1_16xChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.changer.v1_8xChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.changer.v1_9xChanger;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public class VersionExchanger {

    private static MCVersion CORE_VERSION;

    public static String getMinecraftVersion() {
        return Bukkit.getVersion().split(" ")[2].replace(")", "");
    }

    public static MCVersion getCoreVersion() {
        if (CORE_VERSION == null) {
            return CORE_VERSION = MCVersion.fromString(getMinecraftVersion());
        }
        return CORE_VERSION;
    }

    public static VersionChanger getVersionChanger(String minecraft) {
        MCVersion core = MCVersion.fromString(minecraft);
        if (core != MCVersion.Unsupported) {
            PluginUtils.sendConsoleMessage(false,
                Message.GLOBAL_PREFIX.colored() + ' ' + Message.STARTUP_VERSION_SUPPORTED.colored(new String[][] {
                    {
                        "%minecraft%",
                        minecraft
                    },
                    {
                        "%core%",
                        core.name()
                    }
                }));
        }
        switch (core) {
        case v1_8x:
            return new v1_8xChanger();
        case v1_9x:
            return new v1_9xChanger();
        case v1_11x:
            return new v1_11xChanger();
        case v1_13x:
            return new v1_13xChanger();
        case v1_16x:
            return new v1_16xChanger();
        default: {
            PluginUtils.sendConsoleMessage(false,
                Message.GLOBAL_PREFIX.colored() + ' ' + Message.STARTUP_VERSION_UNSUPPORTED.colored(new String[] {
                    "%minecraft%",
                    minecraft
                }));
            PluginUtils.sendConsoleMessage(false, Message.GLOBAL_PREFIX.colored() + ' ' + Message.STARTUP_VERSION_NEED222UPDATE.colored());
            PluginUtils.sendConsoleMessage(false,
                Message.GLOBAL_PREFIX.colored() + ' ' + Message.STARTUP_VERSION_VERSIONS.colored(new String[] {
                    "%versions%",
                    Strings.toString(MCVersion.getSupportedVersions(), Message.GLOBAL_LIST222SPLIT.message())
                }));
            Bukkit.getPluginManager().disablePlugin(PluginUtils.MAIN);
            return null;
        }
        }
    }

    public static boolean checkPermission(WoodType type, Player play) {
        return play.hasPermission("smoothtimber.*") || play.hasPermission("smoothtimber." + type.name().toLowerCase());
    }

    public static boolean checkPermission(String permission, Player play) {
        return play.hasPermission("smoothtimber.*") || play.hasPermission("smoothtimber." + permission);
    }

}
