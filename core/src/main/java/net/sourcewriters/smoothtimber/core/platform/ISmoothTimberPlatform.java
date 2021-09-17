package net.sourcewriters.smoothtimber.core.platform;

import java.util.UUID;

import net.sourcewriters.smoothtimber.core.platform.event.manager.IPlatformEventAdapter;

public interface ISmoothTimberPlatform {

    //
    // Adapters

    /**
     * Returns the platform specific plugin
     * 
     * @return the platform specific plugin
     */
    ISmoothTimberPlugin getPlugin();

    /**
     * Returns the platform specific event adapter
     * 
     * @return the platform specific event adapter
     */
    IPlatformEventAdapter getEventAdapter();

    //
    // Worlds

    /**
     * Returns the amount of worlds that the current platform has loaded
     * 
     * @return the amount of worlds
     */
    int getWorldAmount();

    /**
     * Returns all worlds that the current platform has loaded
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

}
