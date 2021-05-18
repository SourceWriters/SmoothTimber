package com.syntaxphoenix.spigot.smoothtimber.utilities.limit;

import java.util.function.Function;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Parser;

public abstract class Limiter {

    private static Function<CommandSender, Integer> LIMITER;

    public static void setEnabled(boolean enabled) {
        LIMITER = !enabled ? (sender) -> {
            return -1;
        } : (sender) -> {
            return (sender.hasPermission("smoothtimber.*") || sender.hasPermission("smoothtimber.limit.*")) ? -1
                : sender.getEffectivePermissions().stream()
                    .filter(attachment -> attachment.getPermission().startsWith("smoothtimber.limit."))
                    .filter(PermissionAttachmentInfo::getValue).map(attachment -> Parser.parseInt(attachment.getPermission().substring(18)))
                    .min((var1, var2) -> var1.compareTo(var2) * -1).orElse(CutterConfig.DEFAULT_BLOCK_LIMIT);
        };
    }

    public static int getLimit(CommandSender sender) {
        return LIMITER.apply(sender);
    }

}
