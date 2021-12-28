package net.sourcewriters.smoothtimber.api.platform.command;

import com.syntaxphoenix.avinity.command.ISource;

public abstract class PlatformSource implements ISource {
    
    protected final IPlatformSender sender;
    
    public PlatformSource(IPlatformSender sender) {
        this.sender = sender;
    }
    
    @Override
    public boolean hasPermission(String id) {
        return sender.isPermitted(id);
    }
    
    
}
