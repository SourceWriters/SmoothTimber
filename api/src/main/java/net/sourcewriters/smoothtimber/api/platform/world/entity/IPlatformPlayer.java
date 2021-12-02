package net.sourcewriters.smoothtimber.api.platform.world.entity;

import net.sourcewriters.smoothtimber.api.platform.util.STGameMode;

// TODO: Add documentation

public interface IPlatformPlayer extends IPlatformLivingEntity {
    
    int getFoodLevel();
    
    void setFoodLevel(int value);
    
    float getSaturation();
    
    void setSaturation(float value);
    
    STGameMode getGameMode();
    
    void setGameMode(STGameMode gameMode);
    
}
