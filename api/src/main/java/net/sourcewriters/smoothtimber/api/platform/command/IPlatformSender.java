package net.sourcewriters.smoothtimber.api.platform.command;

public interface IPlatformSender {

    /**
     * Sends the sender a message
     * 
     * @param the message
     */
    void send(String message);

    /**
     * Gets the name of the sender
     * 
     * @return the name
     */
    String getName();

    /**
     * Gets if the sender is a player or not
     * 
     * @return if the sender is a player
     */
    boolean isPlayer();

    /**
     * Gets if the sender is a console or not
     * 
     * @return if the sender is a console
     */
    boolean isConsole();

    /**
     * Checks if the sender has a permission
     * 
     * @param  permission the permission to be checked
     * 
     * @return            if the sender is permitted or not
     */
    boolean isPermitted(String permission);

}
