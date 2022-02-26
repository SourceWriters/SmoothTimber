package net.sourcewriters.smoothtimber.api;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.tree.TreeAnimation;
import net.sourcewriters.smoothtimber.api.tree.TreeDetector;

public interface ISmoothTimberRegistry {

    /**
     * Registers a cut down animation
     * 
     * @param  animation                the animation to be registered
     * 
     * @throws IllegalArgumentException if the animation is already registered
     */
    void register(TreeAnimation animation) throws IllegalArgumentException;

    /**
     * Get a specific tree animation
     * 
     * @param  key the key of the animation
     * 
     * @return     the animation or null
     */
    TreeAnimation getAnimation(ResourceKey key);

    /**
     * Gets all tree animations
     * 
     * @return the animations
     */
    TreeAnimation[] getAnimations();

    /**
     * Registers a tree detector
     * 
     * @param  detector                 the detector to be registered
     * 
     * @throws IllegalArgumentException if the detector is already registered
     */
    void register(TreeDetector detector) throws IllegalArgumentException;

    /**
     * Get a specific tree animation
     * 
     * @param  key the key of the animation
     * 
     * @return     the animation or null
     */
    TreeDetector getDetector(ResourceKey key);

    /**
     * Gets all tree detectors
     * 
     * @return the detectors
     */
    TreeDetector[] getDetectors();

}
