package net.sourcewriters.smoothtimber.core.platform.world.entity;

public interface IPlatformArmorStand extends IPlatformLivingEntity {

    /**
     * Gets if the armor stand is small or normal sized
     * 
     * @return if the armor stand is small
     */
    boolean isSmall();

    /**
     * Sets the size of the armor stand
     * 
     * @param small whether the armor stand should be small or normal sized
     */
    void setSmall(boolean small);

}
