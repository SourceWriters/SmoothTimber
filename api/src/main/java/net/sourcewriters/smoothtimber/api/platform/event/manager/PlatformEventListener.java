package net.sourcewriters.smoothtimber.api.platform.event.manager;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import net.sourcewriters.smoothtimber.api.platform.event.PlatformEvent;

@Retention(RUNTIME)
@Target(METHOD)
public @interface PlatformEventListener {

    /**
     * Gets if the listener ignores cancelled events
     * 
     * @return if it ignores cancelled events
     */
    boolean ignoreCancelled() default false;

    /**
     * Gets the priority of the listener
     * 
     * @return the priority
     */
    PlatformEventPriority priority() default PlatformEventPriority.NORMAL;

    /**
     * Gets the event type that the listener is listening to
     * 
     * @return the event type
     */
    Class<? extends PlatformEvent> eventType() default PlatformEvent.class;

}
