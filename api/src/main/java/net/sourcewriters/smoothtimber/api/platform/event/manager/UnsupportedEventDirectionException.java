package net.sourcewriters.smoothtimber.api.platform.event.manager;

public class UnsupportedEventDirectionException extends IllegalArgumentException {

    private static final long serialVersionUID = 54390123409239805L;

    public UnsupportedEventDirectionException() {}

    public UnsupportedEventDirectionException(final String message) {
        super(message);
    }

    public UnsupportedEventDirectionException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
