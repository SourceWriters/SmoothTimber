package net.sourcewriters.smoothtimber.api.platform.command;

import com.syntaxphoenix.avinity.command.ISource;

public abstract class PlatformSource implements ISource {

    protected final IPlatformSender sender;

    public PlatformSource(final IPlatformSender sender) {
        this.sender = sender;
    }

    /**
     * Gets the command sender
     * 
     * @return the sender
     */
    public final IPlatformSender getSender() {
        return sender;
    }

    /**
     * Checks if the sender has a permission
     * 
     * @param  id the permission
     * 
     * @return    if the sender has the permission or not
     */
    @Override
    public final boolean hasPermission(final String id) {
        return sender.isPermitted(id);
    }

}
