package net.sourcewriters.smoothtimber.api.platform.event.manager;

public final class PlatformEventException extends RuntimeException {

    private static final long serialVersionUID = -8421107728958201618L;

    public PlatformEventException(String message) {
        super(message);
    }

    public PlatformEventException(String message, Throwable cause) {
        super(message, cause);
    }

}
