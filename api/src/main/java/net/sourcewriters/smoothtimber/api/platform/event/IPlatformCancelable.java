package net.sourcewriters.smoothtimber.api.platform.event;

public interface IPlatformCancelable {

    void setCancelled(boolean cancelled);

    boolean isCancelled();

}
