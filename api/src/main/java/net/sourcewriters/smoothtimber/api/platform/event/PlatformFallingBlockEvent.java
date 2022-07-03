package net.sourcewriters.smoothtimber.api.platform.event;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformFallingBlock;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public class PlatformFallingBlockEvent extends PlatformEvent {

    private final IPlatformFallingBlock entity;
    private final IPlatformBlock block;
    private final ResourceKey fromType;
    private final ResourceKey toType;

    public PlatformFallingBlockEvent(final IPlatformFallingBlock entity, final IPlatformBlock block, final ResourceKey fromType,
        final ResourceKey toType) {
        this.entity = entity;
        this.block = block;
        this.fromType = fromType;
        this.toType = toType;
    }

    /**
     * Gets the falling block entity
     * 
     * @return the falling block entity
     */
    public IPlatformFallingBlock getEntity() {
        return entity;
    }

    /**
     * The block which is involved in this event
     * 
     * @return the block
     */
    public IPlatformBlock getBlock() {
        return block;
    }

    /**
     * Gets the type that the block was before
     * 
     * @return the old type
     */
    public ResourceKey getFromType() {
        return fromType;
    }

    /**
     * Gets the type that the block will be
     * 
     * @return the new type
     */
    public ResourceKey getToType() {
        return toType;
    }

}
