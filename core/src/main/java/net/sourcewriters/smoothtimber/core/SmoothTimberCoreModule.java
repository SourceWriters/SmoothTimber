package net.sourcewriters.smoothtimber.core;

import com.syntaxphoenix.avinity.module.ModuleWrapper;

import net.sourcewriters.smoothtimber.api.ISmoothTimberRegistry;
import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.core.animation.DefaultAnimation;
import net.sourcewriters.smoothtimber.core.detector.DefaultDetector;
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
        registerAnimations(core.getRegistry());
        registerDetectors(core.getRegistry());
    }
    
    private void registerAnimations(ISmoothTimberRegistry registry) {
        registry.register(new DefaultAnimation(this));
    }
    
    private void registerDetectors(ISmoothTimberRegistry registry) {
        registry.register(new DefaultDetector(this));
    }
    
    
    /*
     * Disable
     */

    @Override
    protected void onDisable() {
        
    }

}
