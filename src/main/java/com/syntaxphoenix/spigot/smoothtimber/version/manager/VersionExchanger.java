package com.syntaxphoenix.spigot.smoothtimber.version.manager;

import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.version.changer.NOPChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.gen.MCVersion;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.gen.MCVersion.MCVersionResult;
import com.syntaxphoenix.syntaxapi.version.DefaultVersion;

public final class VersionExchanger {

    private static VersionChanger VERSION_CHANGER;

    public static VersionChanger getVersionChanger() {
        if (VERSION_CHANGER != null) {
            return VERSION_CHANGER;
        }
        final MCVersionResult result = MCVersion.getCoreVersionResult();
        switch (result.state()) {
        case NOT_TESTED:
            PluginUtils.sendConsoleMessage(false,
                Message.GLOBAL_PREFIX.colored() + ' ' + Message.STARTUP_VERSION_UNTESTED_MSG.colored(new String[][] {
                    {
                        "%minecraft%",
                        MCVersion.getMinecraftVersion().toString()
                    },
                    {
                        "%core%",
                        result.version().name()
                    }
                }));
            PluginUtils.sendConsoleMessage(false,
                Message.GLOBAL_PREFIX.colored() + ' ' + Message.STARTUP_VERSION_UNTESTED_VERSIONS.colored(new String[][] {
                    {
                        "%versions%",
                        result.version().getKnownSupported().stream().map(DefaultVersion::toString)
                            .collect(Collectors.joining(Message.GLOBAL_LIST222SPLIT.message()))
                    }
                }));
            return VERSION_CHANGER = result.version().create();
        case SUPPORTED:
            PluginUtils.sendConsoleMessage(false,
                Message.GLOBAL_PREFIX.colored() + ' ' + Message.STARTUP_VERSION_SUPPORTED.colored(new String[][] {
                    {
                        "%minecraft%",
                        MCVersion.getMinecraftVersion().toString()
                    },
                    {
                        "%core%",
                        result.version().name()
                    }
                }));
            return VERSION_CHANGER = result.version().create();
        default:
            break;
        }
        PluginUtils.sendConsoleMessage(false,
            Message.GLOBAL_PREFIX.colored() + ' ' + Message.STARTUP_VERSION_UNSUPPORTED.colored(new String[] {
                "%minecraft%",
                MCVersion.getMinecraftVersion().toString()
            }));
        PluginUtils.sendConsoleMessage(false, Message.GLOBAL_PREFIX.colored() + ' ' + Message.STARTUP_VERSION_NEED222UPDATE.colored());
        PluginUtils.sendConsoleMessage(false,
            Message.GLOBAL_PREFIX.colored() + ' ' + Message.STARTUP_VERSION_VERSIONS.colored(new String[] {
                "%versions%",
                MCVersion.getAllKnownSupported().stream().map(DefaultVersion::toString)
                    .collect(Collectors.joining(Message.GLOBAL_LIST222SPLIT.message()))
            }));
        Bukkit.getPluginManager().disablePlugin(PluginUtils.MAIN);
        return VERSION_CHANGER = new NOPChanger();
    }

    public static boolean checkPermission(final WoodType type, final Player play) {
        return play.hasPermission("smoothtimber.*") || play.hasPermission("smoothtimber." + type.name().toLowerCase());
    }

    public static boolean checkPermission(final String permission, final Player play) {
        return play.hasPermission("smoothtimber.*") || play.hasPermission("smoothtimber." + permission);
    }

}
