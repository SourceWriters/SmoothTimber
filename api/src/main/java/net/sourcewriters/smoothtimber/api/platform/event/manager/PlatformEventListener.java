package net.sourcewriters.smoothtimber.api.platform.event.manager;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import net.sourcewriters.smoothtimber.api.platform.event.PlatformEvent;

@Retention(RUNTIME)
@Target(METHOD)
public @interface PlatformEventListener {

    boolean ignoreCancelled() default false;

    PlatformEventPriority priority() default PlatformEventPriority.NORMAL;
    
    Class<? extends PlatformEvent> eventType() default PlatformEvent.class;

}
