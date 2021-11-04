package net.sourcewriters.smoothtimber.api.platform.event.manager;

public class UnknownEventException extends IllegalArgumentException {

    private static final long serialVersionUID = 6647379949246312533L;

    public UnknownEventException() {}

    public UnknownEventException(final String message) {
        super(message);
    }

    public UnknownEventException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
