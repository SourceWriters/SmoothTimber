package net.sourcewriters.smoothtimber.api.resource.key;

public class KeyFormatException extends IllegalArgumentException {

    private static final long serialVersionUID = 1974039485316053955L;

    public KeyFormatException() {}

    public KeyFormatException(final String message) {
        super(message);
    }

    public KeyFormatException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
