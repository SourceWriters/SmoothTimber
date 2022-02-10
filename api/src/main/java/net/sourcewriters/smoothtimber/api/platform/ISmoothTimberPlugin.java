package net.sourcewriters.smoothtimber.api.platform;

import java.io.File;

import com.syntaxphoenix.avinity.module.util.DependencyVersion;

public interface ISmoothTimberPlugin {
    
    /**
     * Get System version
     *
     * @return the version
     */
    DependencyVersion getSystemVersion();
    
    /**
     * Get system folder
     * 
     * @return the folder
     */
    File getSystemFolder();
    
    /**
     * Get system jar file
     * 
     * @return the jar file
     */
    File getSystemJar();

}
