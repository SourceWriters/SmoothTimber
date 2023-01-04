package net.sourcewriters.smoothtimber.core;

import com.syntaxphoenix.avinity.module.Module;
import com.syntaxphoenix.avinity.module.ModuleWrapper;
import com.syntaxphoenix.avinity.module.event.ModuleDisableEvent;
import com.syntaxphoenix.avinity.module.event.ModuleEnableEvent;
import com.syntaxphoenix.syntaxapi.event.EventHandler;
import com.syntaxphoenix.syntaxapi.event.EventListener;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;

final class ModuleListener implements EventListener { 
   
    private final SmoothTimberCore core;
    
    public ModuleListener(final SmoothTimberCore core) {
        this.core = core;
    }
    
    @EventHandler
    public void onEnable(ModuleEnableEvent event) {
        
    }
    
    @EventHandler
    public void onDisable(ModuleDisableEvent event) {
        ModuleWrapper<?> wrapper = event.getWrapper();
        Module module = wrapper.getModule();
        if(module == null || !(module instanceof SmoothTimberModule)) {
            return;
        }
        core.getRegistry().unregister(module.getId());
        core.getApi().getKeyCache().clearNamespace(module.getId());
    }

}
