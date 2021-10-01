package net.sourcewriters.smoothtimber.core.platform.world.inventory;

import net.sourcewriters.smoothtimber.core.resource.key.ResourceKey;

// TODO(Lauriichan): Add documentation

public interface IPlatformItem {

    ResourceKey getType();

    boolean hasType(ResourceKey key);

    boolean setType(ResourceKey key);

    int getMaxAmount();

    int getMaxDurability();

    IPlatformItemMeta meta();

}
