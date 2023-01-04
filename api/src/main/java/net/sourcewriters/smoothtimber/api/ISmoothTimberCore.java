package net.sourcewriters.smoothtimber.api;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.util.IResource;

public interface ISmoothTimberCore {

    /**
     * Gets the system logger
     * 
     * @return the logger
     */
    ILogger getSystemLogger();

    /**
     * Gets the system resource
     * 
     * @return the resource
     */
    IResource getSystemResource();

    /**
     * Gets or creates the Logger for the module
     * 
     * @param  module the module that the logger is for
     * 
     * @return        the module logger
     */
    ILogger getLogger(SmoothTimberModule module);

    /**
     * Gets or creates the resource for module
     * 
     * @param  module the module that the resource is for
     * 
     * @return        the resource
     */
    IResource getResource(SmoothTimberModule module);
    
    /**
     * Gets the SmoothTimber tree permission manager
     * 
     * @return the permission manager
     */
    ISmoothTimberPermissionManager getPermissionManager();

    /**
     * Gets the SmoothTimber registry
     * 
     * @return the registry
     */
    ISmoothTimberRegistry getRegistry();

}
