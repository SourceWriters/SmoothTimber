package net.sourcewriters.smoothtimber.api.platform.event;

import net.sourcewriters.smoothtimber.api.platform.world.IPlatformBlock;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntity;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public class PlatformEntityChangeBlockEvent extends PlatformEvent {

    private final IPlatformEntity entity;
    private final IPlatformBlock block;
    private final ResourceKey fromType;
    private final ResourceKey toType;
    
    private boolean cancelled;

    public PlatformEntityChangeBlockEvent(final IPlatformEntity entity, final IPlatformBlock block, final ResourceKey fromType,
        final ResourceKey toType) {
        this.entity = entity;
        this.block = block;
        this.fromType = fromType;
        this.toType = toType;
    }

    /**
     * Gets the involved entity
     * 
     * @return the entity
     */
    public IPlatformEntity getEntity() {
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

    /**
     * Checks if the event is cancelled
     * 
     * @return if the event is cancelled or not
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancel state of the event
     * 
     * @param cancelled the state to set
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
