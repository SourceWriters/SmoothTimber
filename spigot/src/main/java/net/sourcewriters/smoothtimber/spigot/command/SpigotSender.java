package net.sourcewriters.smoothtimber.spigot.command;

import org.bukkit.command.CommandSender;

import net.sourcewriters.smoothtimber.api.platform.command.IPlatformPermission;
import net.sourcewriters.smoothtimber.api.platform.command.IPlatformSender;

public class SpigotSender<P extends IPlatformSender, E extends CommandSender> implements IPlatformSender {

    protected final E handle;

    public SpigotSender(final E handle) {
        this.handle = handle;
    }

    @Override
    public void send(String message) {
        handle.sendMessage(message);
    }

    @Override
    public String getName() {
        return handle.getName();
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean isEntity() {
        return false;
    }

    @Override
    public boolean isConsole() {
        return true;
    }

    @Override
    public CommandSender getHandle() {
        return handle;
    }

    @Override
    public boolean isPermitted(String permission) {
        return handle.hasPermission(permission);
    }

    @Override
    public IPlatformPermission getPermission(String permission) {
        return new SpigotPermission(this, permission);
    }

}
