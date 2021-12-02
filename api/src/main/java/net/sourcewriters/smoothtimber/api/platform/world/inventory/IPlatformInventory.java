package net.sourcewriters.smoothtimber.api.platform.world.inventory;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

// TODO: Add documentation

public interface IPlatformInventory {
    
    void add(IPlatformItem item);
    
    void set(int slot, IPlatformItem item);
    
    void set(int column, int row, IPlatformItem item);
    
    IPlatformItem get(int slot);
    
    IPlatformItem get(int column, int row);
    
    String getName();
    
    void setName(String name);
    
    int getSize();
    
    void setSize(int size);
    
    ResourceKey getType();
    
    boolean setType(ResourceKey key);
    
}
