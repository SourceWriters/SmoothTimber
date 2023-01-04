package net.sourcewriters.smoothtimber.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import net.sourcewriters.smoothtimber.api.ISmoothTimberRegistry;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.tree.ITree;
import net.sourcewriters.smoothtimber.api.tree.IBlockType;
import net.sourcewriters.smoothtimber.api.tree.TreeAnimation;
import net.sourcewriters.smoothtimber.api.tree.TreeDetector;
import net.sourcewriters.smoothtimber.api.util.math.Vector3i;
import net.sourcewriters.smoothtimber.api.util.world.PlatformBlockCache;

final class SmoothTimberCoreRegistry implements ISmoothTimberRegistry {

    private final ConcurrentHashMap<String, IBlockType> blockTypes = new ConcurrentHashMap<>();
    private final List<IBlockType> blockTypeList = Collections.synchronizedList(new ArrayList<>());

    private final ConcurrentHashMap<String, TreeAnimation> animations = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, TreeDetector> detectors = new ConcurrentHashMap<>();

    final void unregister(String id) {
        IBlockType[] blockTypeArray = getBlockTypes();
        for (IBlockType blockType : blockTypeArray) {
            if (blockType.id().getNamespace().equals(id)) {
                blockTypes.remove(blockType.id().toResourceString());
            }
        }
        TreeAnimation[] animationArray = getAnimations();
        for (TreeAnimation animation : animationArray) {
            if (animation.getKey().getKey().equals(id)) {
                animations.remove(animation.getKey().toResourceString());
            }
        }
        TreeDetector[] detectorArray = getDetectors();
        for (TreeDetector detector : detectorArray) {
            if (detector.getKey().getKey().equals(id)) {
                detectors.remove(detector.getKey().toResourceString());
            }
        }
    }

    @Override
    public boolean register(IBlockType blockType) throws IllegalArgumentException {
        Objects.requireNonNull(blockType, "IBlockType can't be null!");
        String id = blockType.id().toResourceString();
        if (blockTypes.containsKey(id)) {
            throw new IllegalArgumentException("A IBlockType with the name '" + id + "' already exists!");
        }
        if (!blockType.isSupported()) {
            return false;
        }
        blockTypes.put(id, blockType);
        blockTypeList.add(blockType);
        return true;
    }

    @Override
    public IBlockType getBlockTypeByMaterial(ResourceKey material) {
        return blockTypeList.stream().filter(type -> type.isType(material)).findFirst().orElse(null);
    }

    @Override
    public IBlockType getBlockType(String name) {
        return blockTypes.get(name);
    }

    @Override
    public IBlockType[] getBlockTypes() {
        return blockTypeList.toArray(IBlockType[]::new);
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
