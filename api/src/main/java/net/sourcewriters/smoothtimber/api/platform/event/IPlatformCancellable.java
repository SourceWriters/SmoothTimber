package net.sourcewriters.smoothtimber.api.platform.event;

public interface IPlatformCancellable {

    /**
     * Sets if the event is cancelled or not
     * 
     * @param the cancelled state
     */
    void setCancelled(boolean cancelled);

    /**
     * Gets if the event is cancelled
     * 
     * @return if the event is cancelled or not
     */
    boolean isCancelled();

}
