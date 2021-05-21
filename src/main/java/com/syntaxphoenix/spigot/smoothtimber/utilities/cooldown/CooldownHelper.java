package com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown;

import java.util.UUID;
import java.util.function.Function;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Parser;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Strings;

public final class CooldownHelper {

    public static final CooldownBuilder COOLDOWN = new CooldownBuilder();

    private static Function<Player, Long> COOLDOWN_TIME;

    private CooldownHelper() {}

    public static void setEnabled(boolean enabled) {
        COOLDOWN.getTimer().setRunning(enabled);
        COOLDOWN_TIME = !enabled ? (sender) -> {
            return -1L;
        } : (sender) -> {
            return (sender.hasPermission("smoothtimber.*") || sender.hasPermission("smoothtimber.cooldown.*")) ? -1L
                : sender.getEffectivePermissions().stream()
                    .filter(attachment -> attachment.getPermission().startsWith("smoothtimber.cooldown."))
                    .filter(PermissionAttachmentInfo::getValue)
                    .map(attachment -> Parser.parseLong(attachment.getPermission().substring(22)))
                    .min((var1, var2) -> var1.compareTo(var2) * -1).orElse(CutterConfig.DEFAULT_COOLDOWN_TIME);
        };
    }

    public static long getCooldown(Player sender) {
        return COOLDOWN_TIME.apply(sender);
    }

    public static boolean isTriggered(UUID uniqueId) {
        return COOLDOWN.get(uniqueId).isTriggerable();
    }

    public static void trigger(Player sender) {
        Cooldown cooldown = COOLDOWN.get(sender.getUniqueId());
        long value = getCooldown(sender);
        if (value <= 0) {
            return;
        }
        cooldown.setCooldown(value);
        cooldown.trigger();
    }

    public static String getFormattedTime(UUID uniqueId) {
        long value = COOLDOWN.get(uniqueId).getTreshhold();
        return Strings.insertPeriodically("" + value, ".", 3) + Message.TIME_MILISECOND.message();
    }

    public static void reset(Player player) {
        COOLDOWN.get(player.getUniqueId()).reset();
    }

}
