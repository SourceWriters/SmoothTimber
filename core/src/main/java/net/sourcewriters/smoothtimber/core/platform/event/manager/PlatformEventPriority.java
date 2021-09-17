package net.sourcewriters.smoothtimber.core.platform.event.manager;

/**
 * The priority which event listeners can used to be called before or after
 * other listeners
 * 
 * They're called in this order:
 * <ol>
 * <li>HIGHEST
 * <li>HIGH
 * <li>NORMAL
 * <li>LOW
 * <li>LOWEST
 * <li>Monitor
 */
public enum PlatformEventPriority {

    MONITOR,
    LOWEST,
    LOW,
    NORMAL,
    HIGH,
    HIGHEST

}
