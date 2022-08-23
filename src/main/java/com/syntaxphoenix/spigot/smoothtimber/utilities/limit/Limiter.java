package com.syntaxphoenix.spigot.smoothtimber.utilities.limit;

import java.util.function.Function;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Parser;

public final class Limiter {

    private Limiter() {
        throw new UnsupportedOperationException();
    }

    private static Function<CommandSender, Integer> LIMITER;

    private static final Function<CommandSender, Integer> NO_LIMIT = (sender) -> -1;
    private static final Function<CommandSender, Integer> LIMITED = (sender) -> {
        if(sender.hasPermission("smoothtimber.*") || sender.hasPermission("smoothtimber.limit.*")) {
            return -1;
        }
        return sender.getEffectivePermissions().stream()
            .filter(attachment -> attachment.getPermission().startsWith("smoothtimber.limit."))
            .filter(PermissionAttachmentInfo::getValue)
            .map(attachment -> Parser.parseInt(attachment.getPermission().substring(19)))
            .min((var1, var2) -> var1.compareTo(var2) * -1).orElse(CutterConfig.DEFAULT_BLOCK_LIMIT);
    };

    public static void setEnabled(boolean enabled) {
        if (enabled) {
            LIMITER = LIMITED;
            return;
        }
        LIMITER = NO_LIMIT;
    }

    public static int getLimit(CommandSender sender) {
        return LIMITER.apply(sender);
    }

}
