package net.sourcewriters.smoothtimber.core;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import net.sourcewriters.smoothtimber.api.ISmoothTimberRegistry;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.tree.ITree;
import net.sourcewriters.smoothtimber.api.tree.IWoodType;
import net.sourcewriters.smoothtimber.api.tree.TreeAnimation;
import net.sourcewriters.smoothtimber.api.tree.TreeDetector;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;
import net.sourcewriters.smoothtimber.api.util.world.PlatformBlockCache;

public class SmoothTimberCoreRegistry implements ISmoothTimberRegistry {

    private final ConcurrentHashMap<String, IWoodType> woodTypes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, TreeAnimation> animations = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, TreeDetector> detectors = new ConcurrentHashMap<>();

    @Override
    public boolean register(IWoodType woodType) throws IllegalArgumentException {
        Objects.requireNonNull(woodType, "IWoodType can't be null!");
        if (woodTypes.containsKey(woodType.name())) {
            throw new IllegalArgumentException("A IWoodType with the name '" + woodType.name() + "' already exists!");
        }
        if (!woodType.isSupported()) {
            return false;
        }
        woodTypes.put(woodType.name(), woodType);
        return true;
    }

    @Override
    public IWoodType getWoodType(String name) {
        return woodTypes.get(name);
    }

    @Override
    public IWoodType[] getWoodTypes() {
        return woodTypes.values().toArray(IWoodType[]::new);
    }

    @Override
    public void register(TreeAnimation animation) throws IllegalArgumentException {
        Objects.requireNonNull(animation, "TreeAnimation can't be null!");
        String key = animation.getKey().toResourceString();
        if (animations.containsKey(key)) {
            throw new IllegalArgumentException("A TreeAnimation with the namespaced key '" + key + "' already exists!");
        }
        animations.put(key, animation);
    }

    @Override
    public TreeAnimation getAnimation(ResourceKey key) {
        Objects.requireNonNull(key, "ResourceKey can't be null!");
        return animations.get(key.toResourceString());
    }

    @Override
    public TreeAnimation[] getAnimations() {
        return animations.values().toArray(TreeAnimation[]::new);
    }

    @Override
    public void register(TreeDetector detector) throws IllegalArgumentException {
        Objects.requireNonNull(detector, "TreeDetector can't be null!");
        String key = detector.getKey().toResourceString();
        if (detectors.containsKey(key)) {
            throw new IllegalArgumentException("A TreeDetector with the namespaced key '" + key + "' already exists!");
        }
        detectors.put(key, detector);
    }

    @Override
    public TreeDetector getDetector(ResourceKey key) {
        Objects.requireNonNull(key, "ResourceKey can't be null!");
        return detectors.get(key.toResourceString());
    }

    @Override
    public TreeDetector[] getDetectors() {
        return detectors.values().toArray(TreeDetector[]::new);
    }

    @Override
    public ITree detectTree(IPlatformBlock block) {
        return detectTree(block.getWorld(), block.getPosition());
    }

    @Override
    public ITree detectTree(IPlatformWorld world, Vector3i location) {
        PlatformBlockCache cache = new PlatformBlockCache(world);
        TreeDetector[] detectors = getDetectors();
        for (TreeDetector detector : detectors) {
            ITree tree = detector.detect(cache, location.clone());
            if (tree == null) {
                continue;
            }
            return tree;
        }
        return null;
    }

}
