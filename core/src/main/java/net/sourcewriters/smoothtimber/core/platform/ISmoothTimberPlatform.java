package net.sourcewriters.smoothtimber.core.platform;

import java.util.UUID;

import net.sourcewriters.smoothtimber.core.platform.event.manager.IPlatformEventAdapter;
import net.sourcewriters.smoothtimber.core.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.core.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.core.platform.world.inventory.IPlatformInventory;
import net.sourcewriters.smoothtimber.core.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.core.resource.key.ResourceKey;

public interface ISmoothTimberPlatform {

    //
    // Adapters

    /**
     * Gets the platform specific plugin
     * 
     * @return the platform specific plugin
     */
    ISmoothTimberPlugin getPlugin();

    /**
     * Gets the platform specific event adapter
     * 
     * @return the platform specific event adapter
     */
    IPlatformEventAdapter getEventAdapter();

    //
    // Players

    /**
     * Gets all online players
     * 
     * @return the players
     */
    IPlatformPlayer[] getPlayers();

    //
    // Worlds

    /**
     * Gets the amount of worlds that the current platform has loaded
     * 
     * @return the amount of worlds
     */
    int getWorldAmount();

    /**
     * Gets all worlds that the current platform has loaded
     * 
     * @return all worlds
     */
    IPlatformWorld getWorlds();

    /**
     * Gets the world at the specific index
     * 
     * @param  index the index of the world
     * 
     * @return       the world at the index or null if the world doesn't exist
     */
    IPlatformWorld getWorldByIndex(int index);

    /**
     * Gets the world with the specific id
     * 
     * @param  uniqueId the id of the world
     * 
     * @return          the world with the id or null if the world doesn't exist
     */
    IPlatformWorld getWorldById(UUID uniqueId);

    /**
     * Gets the world with the specified name
     * 
     * @param  name the name of the world
     * 
     * @return      the world with the name or null if the world doesn't exist
     */
    IPlatformWorld getWorldByName(String name);

    //
    // Inventory

    // TODO(Lauriichan): Add documentation

    IPlatformItem buildItem(ResourceKey key);

    IPlatformInventory buildInventory();

}
