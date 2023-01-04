package net.sourcewriters.smoothtimber.api;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.tree.ITree;
import net.sourcewriters.smoothtimber.api.tree.IBlockType;
import net.sourcewriters.smoothtimber.api.tree.TreeAnimation;
import net.sourcewriters.smoothtimber.api.tree.TreeDetector;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;

public interface ISmoothTimberRegistry {

    /**
     * Registers a block type
     * 
     * @param  blockType                the block type to be registered
     * 
     * @return                          {@code true} if it was registered
     *                                      successfully or {@code false} if the
     *                                      block type is not supported and
     *                                      therefore wasn't registered
     * 
     * @throws IllegalArgumentException if the block type is already registered
     */
    boolean register(IBlockType blockType) throws IllegalArgumentException;

    /**
     * Tries to find a block type by material key
     * 
     * @param  material the material resource key
     * 
     * @return          the block type or {@code null} if unsupported or unavailable
     */
    IBlockType getBlockTypeByMaterial(ResourceKey material);

    /**
     * Get a specific block type
     * 
     * @param  name the name of the block type
     * 
     * @return      the block type or null
     */
    IBlockType getBlockType(String name);

    /**
     * Gets all block types
     * 
     * @return the block types
     */
    IBlockType[] getBlockTypes();

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

    /**
     * Detects if a tree is at a location
     * 
     * @param  world    the world the location is in
     * @param  location the location to detect a tree from
     * 
     * @return          the tree or null
     */
    ITree detectTree(IPlatformBlock block);

    /**
     * Detects if a tree is at a location
     * 
     * @param  world    the world the location is in
     * @param  location the location to detect a tree from
     * 
     * @return          the tree or null
     */
    ITree detectTree(IPlatformWorld world, Vector3i location);

}
