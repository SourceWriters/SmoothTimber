package net.sourcewriters.smoothtimber.api.platform.command;

public interface IPlatformPermission {
    
    /**
     * Gets the platform sender this permission relates to
     * 
     * @return the platform sender
     */
    IPlatformSender getSender();

    /**
     * Checks if the permission is set
     * 
     * @return {@code true} if the permission is set otherwise {@code false}
     */
    boolean isSet();

    /**
     * Checks if the permission is allowed
     * 
     * @return {@code true} if the permission is allowed otherwise {@code false}
     */
    boolean isAllowed();

    /**
     * Sets if the permission is allowed
     * 
     * @param allowed the allowed state
     */
    void setAllowed(boolean allowed);

    /**
     * Checks if the permission has a value
     * 
     * @return {@code true} if the permission has a value otherwise {@code false}
     */
    default boolean hasValue() {
        return getValue() != null;
    }

    /**
     * Sets the value of the permission
     * 
     * @param value the value to set
     */
    void setValue(String value);

    /**
     * Sets the value of the permission
     * 
     * @param value the value to set
     */
    default void setValue(Object value) {
        if (value == null) {
            setValue(null);
            return;
        }
        setValue(value.toString());
    }

    /**
     * Gets the value of the permission
     * 
     * @return the value of the permission or {@code null} if none is available
     */
    String getValue();

}
