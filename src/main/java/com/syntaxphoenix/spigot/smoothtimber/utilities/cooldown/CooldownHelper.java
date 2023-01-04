package com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown;

import java.util.UUID;
import java.util.function.Function;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Parser;

public final class CooldownHelper {

    public static final CooldownBuilder COOLDOWN = new CooldownBuilder();

    private static Function<Player, Long> COOLDOWN_TIME;

    private CooldownHelper() {}

    public static void setEnabled(final boolean enabled) {
        COOLDOWN_TIME = !enabled ? sender -> -1L
            : sender -> (sender.hasPermission("smoothtimber.*") || sender.hasPermission("smoothtimber.cooldown.*") ? -1L
                : sender.getEffectivePermissions().stream()
                    .filter(attachment -> attachment.getPermission().startsWith("smoothtimber.cooldown."))
                    .filter(PermissionAttachmentInfo::getValue)
                    .map(attachment -> Parser.parseLong(attachment.getPermission().substring(22)))
                    .min((var1, var2) -> var1.compareTo(var2) * -1).orElse(CutterConfig.DEFAULT_COOLDOWN_TIME));
        COOLDOWN.getTimer().setRunning(enabled);
    }

    public static long getCooldown(final Player sender) {
        return COOLDOWN_TIME.apply(sender);
    }

    public static boolean isTriggered(final UUID uniqueId) {
        return !COOLDOWN.get(uniqueId).isTriggerable();
    }

    public static void trigger(final Player sender) {
        final Cooldown cooldown = COOLDOWN.get(sender.getUniqueId());
        final long value = getCooldown(sender);
        if (value <= 0) {
            return;
        }
        cooldown.setCooldown(value);
        cooldown.trigger();
    }

    public static String getFormattedTime(final UUID uniqueId) {
        long val = COOLDOWN.get(uniqueId).getTreshhold();
        val = Math.round((double) val / 100);
        final long dez = val % 10;
        return (val - dez) / 10 + "." + dez + " " + (val > 1500 ? Message.TIME_SECONDS.message() : Message.TIME_SECOND.message());
    }

    public static void reset(final Player player) {
        COOLDOWN.get(player.getUniqueId()).reset();
    }

}
