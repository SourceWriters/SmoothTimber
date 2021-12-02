package net.sourcewriters.smoothtimber.api.platform.world.inventory;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

// TODO: Add documentation

public interface IPlatformItem {

    ResourceKey getType();

    boolean hasType(ResourceKey key);

    boolean setType(ResourceKey key);

    int getMaxAmount();

    int getMaxDurability();

    IPlatformItemMeta meta();

}
