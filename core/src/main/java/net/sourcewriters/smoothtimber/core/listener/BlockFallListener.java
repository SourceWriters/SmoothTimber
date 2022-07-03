package net.sourcewriters.smoothtimber.core.listener;

import com.syntaxphoenix.avinity.module.extension.Extension;

import net.sourcewriters.smoothtimber.api.platform.event.PlatformFallingBlockEvent;
import net.sourcewriters.smoothtimber.api.platform.event.manager.IPlatformEventListener;
import net.sourcewriters.smoothtimber.api.platform.event.manager.PlatformEventListener;

@Extension
public final class BlockFallListener implements IPlatformEventListener {

    @PlatformEventListener
    public void onBlockFall(PlatformFallingBlockEvent event) {
        // TODO: Implement fall (@default module)
    }

}
