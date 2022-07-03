package net.sourcewriters.smoothtimber.api.platform.command;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface Command {

    /**
     * Gets the name of the command
     * 
     * @return the name
     */
    String name();

    /**
     * Gets the aliases of the command
     * 
     * @return the aliases
     */
    String[] alias() default {};

}
