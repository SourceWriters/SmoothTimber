package net.sourcewriters.smoothtimber.spigot.command;

import net.sourcewriters.smoothtimber.api.platform.command.IPlatformPermission;
import net.sourcewriters.smoothtimber.api.platform.command.IPlatformSender;

final class SpigotPermission implements IPlatformPermission {
    
    private final String name;
    private final SpigotSender<?, ?> sender;
    
    public SpigotPermission(SpigotSender<?, ?> sender, String name) {
        this.sender = sender;
        this.name = name;
        
        // TODO: Find out how to do this
    }

    @Override
    public IPlatformSender getSender() {
        return sender;
    }

    @Override
    public boolean isSet() {
        return false;
    }

    @Override
    public boolean isAllowed() {
        return false;
    }

    @Override
    public void setAllowed(boolean allowed) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setValue(String value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getValue() {
        // TODO Auto-generated method stub
        return null;
    }

}
