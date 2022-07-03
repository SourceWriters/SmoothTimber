package net.sourcewriters.smoothtimber.api.platform.world.entity;

import net.sourcewriters.smoothtimber.api.platform.util.STGameMode;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformInventory;

public interface IPlatformPlayer extends IPlatformLivingEntity {

    @Override
    default boolean isPlayer() {
        return true;
    }

    /**
     * Gets the inventory of the player
     * 
     * @return the player inventory
     */
    IPlatformInventory getInventory();

    /**
     * Gets the current food level of the player
     * 
     * @return the players food level
     */
    int getFoodLevel();

    /**
     * Sets the food level of the player
     * 
     * @param value the food level to be set
     */
    void setFoodLevel(int value);

    /**
     * Gets the current saturation value of the player
     * 
     * @return the players saturation value
     */
    float getSaturation();

    /**
     * Sets the saturation value of the player
     * 
     * @param value the saturation value to be set
     */
    void setSaturation(float value);

    /**
     * Gets the game mode of the player
     * 
     * @return the players game mode
     */
    STGameMode getGameMode();

    /**
     * Sets the game mode of the player
     * 
     * @param gameMode the game mode to be set
     */
    void setGameMode(STGameMode gameMode);

    /**
     * Opens a inventory
     * 
     * @param inventory the inventory to be opened
     */
    void openInventory(IPlatformInventory inventory);

}
