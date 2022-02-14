package net.sourcewriters.smoothtimber.api;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.util.IResource;

public interface ISmoothTimberCore {

    /**
     * Get the system logger
     * 
     * @return the logger
     */
    ILogger getSystemLogger();

    /**
     * Get the system resource
     * 
     * @return the resource
     */
    IResource getSystemResource();

    /**
     * Get or create Logger for module
     * 
     * @param  module the module that the logger is for
     * 
     * @return        the module logger
     */
    ILogger getLogger(SmoothTimberModule module);

    /**
     * Get or create resource for module
     * 
     * @param  module the module that the resource is for
     * 
     * @return        the resource
     */
    IResource getResource(SmoothTimberModule module);

    /**
     * Get the SmoothTimber registry
     * 
     * @return the registry
     */
    ISmoothTimberRegistry getRegistry();

}
