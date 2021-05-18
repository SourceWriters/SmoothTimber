package com.syntaxphoenix.spigot.smoothtimber.utilities;

import org.bukkit.entity.Player;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionExchanger;

public final class PlayerState {

    private PlayerState() {}

    public static boolean isActive(Player player) {
        return isPermitted(player) && isWorldEnabled(player) && CutterConfig.SNEAK.test(() -> player.isSneaking())
            && CutterConfig.TOGGLEABLE.test(() -> SmoothTimber.STORAGE.hasToggled(player.getUniqueId()));
    }

    public static boolean isPermitted(Player player) {
        return VersionExchanger.checkPermission("use", player);
    }

    public static boolean isWorldEnabled(Player player) {
        if (!CutterConfig.ENABLE_WORLD) {
            return true;
        }
        boolean contains = CutterConfig.WORLD_LIST.contains(player.getWorld().getName());
        return CutterConfig.ENABLE_WORLD_BLACKLIST ? contains : !contains;
    }

    public static boolean isToggled(Player player) {
        return SmoothTimber.STORAGE.hasToggled(player.getUniqueId());
    }

}
