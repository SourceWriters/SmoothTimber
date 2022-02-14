package net.sourcewriters.smoothtimber.api.tree;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public interface IWoodType {
    
    String name();
    
    ResourceKey key();
    
    boolean isSupported();
    
    boolean isType(ResourceKey key);

}
