package net.sourcewriters.smoothtimber.core.listener;

import com.syntaxphoenix.avinity.module.ModuleWrapper;
import com.syntaxphoenix.avinity.module.extension.Extension;

import net.sourcewriters.smoothtimber.api.ISmoothTimberRegistry;
import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlatform;
import net.sourcewriters.smoothtimber.api.platform.event.PlatformBlockBreakEvent;
import net.sourcewriters.smoothtimber.api.platform.event.manager.IPlatformEventListener;
import net.sourcewriters.smoothtimber.api.platform.event.manager.PlatformEventListener;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.tree.ITree;

@Extension
public final class BlockBreakListener implements IPlatformEventListener {

    private final ISmoothTimberPlatform platform;
    private final ISmoothTimberRegistry registry;

    public BlockBreakListener(ModuleWrapper<SmoothTimberModule> module) {
        this.platform = module.getModule().getApi().getPlatform();
        this.registry = module.getModule().getCore().getRegistry();
    }

    // TODO: Per tree animations
    
    // TODO: **ADD CONFIG**

    // TODO: maybe allow to disable detectors?

    @PlatformEventListener(ignoreCancelled = true)
    public void onBlockBreak(PlatformBlockBreakEvent event) {
        IPlatformPlayer player = event.getPlayer();
        ITree tree = registry.detectTree(event.getBlock());
        if (tree == null) {
            return;
        }
        
    }

}
