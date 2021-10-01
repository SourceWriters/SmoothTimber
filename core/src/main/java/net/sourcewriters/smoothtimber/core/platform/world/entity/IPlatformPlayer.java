package net.sourcewriters.smoothtimber.core.platform.world.entity;

import net.sourcewriters.smoothtimber.core.platform.util.STGameMode;

// TODO(Lauriichan): Add documentation

public interface IPlatformPlayer extends IPlatformLivingEntity {
    
    int getFoodLevel();
    
    void setFoodLevel(int value);
    
    float getSaturation();
    
    void setSaturation(float value);
    
    STGameMode getGameMode();
    
    void setGameMode(STGameMode gameMode);
    
}
