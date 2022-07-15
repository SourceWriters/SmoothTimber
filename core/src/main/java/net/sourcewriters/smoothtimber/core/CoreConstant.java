package net.sourcewriters.smoothtimber.core;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public final class CoreConstant {

    private CoreConstant() {
        throw new UnsupportedOperationException();
    }
    
    public static final ResourceKey FALL_DATA_KEY = new ResourceKey("smoothtimber", "fall-data");
    public static final ResourceKey FALL_COLLECT_KEY = new ResourceKey("smoothtimber", "fall-collect");
    
}
