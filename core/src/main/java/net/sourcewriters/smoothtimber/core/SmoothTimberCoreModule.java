package net.sourcewriters.smoothtimber.core;

import com.syntaxphoenix.avinity.module.ModuleWrapper;

import net.sourcewriters.smoothtimber.api.ISmoothTimberRegistry;
import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlatform;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKeyCache;
import net.sourcewriters.smoothtimber.api.tree.SimpleBlockType;
import net.sourcewriters.smoothtimber.core.animation.*;
import net.sourcewriters.smoothtimber.core.detector.*;
import net.sourcewriters.smoothtimber.core.util.JavaAccess;

final class SmoothTimberCoreModule extends SmoothTimberModule {

    public SmoothTimberCoreModule(ModuleWrapper<?> wrapper) {
        JavaAccess.setObjectValue(this, getClass(), "wrapper", wrapper);
    }

    /*
     * Enable
     */

    @Override
    protected void onEnable() {
        registerWoodTypes(api.getKeyCache(), api.getPlatform(), core.getRegistry());
        registerAnimations(core.getRegistry());
        registerDetectors(core.getRegistry());
    }

    private void registerWoodTypes(ResourceKeyCache keys, ISmoothTimberPlatform platform, ISmoothTimberRegistry registry) {
        registry.register(new SimpleBlockType(platform, true, key("oak_wood"), keys.minecraft("oak_log"), keys.minecraft("oak_wood"),
            keys.minecraft("stripped_oak_log"), keys.minecraft("stripped_oak_wood")));
        registry.register(new SimpleBlockType(platform, true, key("birch_wood"), keys.minecraft("birch_log"), keys.minecraft("birch_wood"),
            keys.minecraft("stripped_birch_log"), keys.minecraft("stripped_birch_wood")));
        registry.register(new SimpleBlockType(platform, true, key("spruce_wood"), keys.minecraft("spruce_log"),
            keys.minecraft("spruce_wood"), keys.minecraft("stripped_spruce_log"), keys.minecraft("stripped_spruce_wood")));
        registry.register(new SimpleBlockType(platform, true, key("jungle_wood"), keys.minecraft("jungle_log"),
            keys.minecraft("jungle_wood"), keys.minecraft("stripped_jungle_log"), keys.minecraft("stripped_jungle_wood")));
        registry.register(new SimpleBlockType(platform, true, key("dark_oak_wood"), keys.minecraft("dark_oak_log"),
            keys.minecraft("dark_oak_wood"), keys.minecraft("stripped_dark_oak_log"), keys.minecraft("stripped_dark_oak_wood")));
        registry.register(new SimpleBlockType(platform, true, key("acacia_wood"), keys.minecraft("acacia_log"),
            keys.minecraft("acacia_wood"), keys.minecraft("stripped_acacia_log"), keys.minecraft("stripped_acacia_wood")));
    }

    private void registerAnimations(ISmoothTimberRegistry registry) {
        registry.register(new SimpleFallingBlockAnimation(this));
    }

    private void registerDetectors(ISmoothTimberRegistry registry) {
        registry.register(new SmallTreeDetector(this));
        registry.register(new MediumTreeDetector(this));
        registry.register(new BigTreeDetector(this));
    }

    /*
     * Disable
     */

    @Override
    protected void onDisable() {

    }

}
