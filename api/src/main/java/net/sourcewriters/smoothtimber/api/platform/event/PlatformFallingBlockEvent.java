package net.sourcewriters.smoothtimber.api.platform.event;

import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformFallingBlock;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public class PlatformFallingBlockEvent extends PlatformEvent {

    private final IPlatformFallingBlock entity;
    private final ResourceKey fromType;
    private final ResourceKey toType;

    public PlatformFallingBlockEvent(final IPlatformFallingBlock entity, final ResourceKey fromType, final ResourceKey toType) {
        this.entity = entity;
        this.fromType = fromType;
        this.toType = toType;
    }

    public IPlatformFallingBlock getEntity() {
        return entity;
    }

    public ResourceKey getFromType() {
        return fromType;
    }

    public ResourceKey getToType() {
        return toType;
    }

}
