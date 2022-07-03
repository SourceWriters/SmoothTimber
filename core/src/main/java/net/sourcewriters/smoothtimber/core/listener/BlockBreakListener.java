package net.sourcewriters.smoothtimber.core.listener;

import com.syntaxphoenix.avinity.module.extension.Extension;

import net.sourcewriters.smoothtimber.api.platform.event.PlatformBlockBreakEvent;
import net.sourcewriters.smoothtimber.api.platform.event.manager.IPlatformEventListener;
import net.sourcewriters.smoothtimber.api.platform.event.manager.PlatformEventListener;

@Extension
public final class BlockBreakListener implements IPlatformEventListener {

    @PlatformEventListener
    public void onBlockBreak(PlatformBlockBreakEvent event) {
        // TODO: Implement block break
    }
    
}
