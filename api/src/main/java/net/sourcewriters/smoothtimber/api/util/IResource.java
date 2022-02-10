package net.sourcewriters.smoothtimber.api.util;

import java.nio.file.Path;

import net.sourcewriters.smoothtimber.api.util.source.DataSource;

public interface IResource {

    /**
     * Get jar root path
     * 
     * @return the root path
     */
    Path getRootPath();

    /**
     * Get path of resource inside of jar
     * 
     * @param  path the relative path inside of the jar
     * 
     * @return      the path of the resource
     */
    Path getPath(final String path);

    /**
     * Get data source of resource inside of jar
     * 
     * @param  path the relative path inside of the jar
     * 
     * @return      the data source of the resource
     */
    DataSource getSource(final String path);

    /**
     * Copies a resource inside of the jar into the module specific data folder and
     * returns the path to the copied resource outside of the jar.
     * 
     * @param  path the relative path inside of the jar
     * 
     * @return      the external path of the resource
     */
    Path getPathAsExternal(final String path);

    /**
     * Copies a resource inside of the jar into the module specific data folder and
     * returns the data source to the copied resource outside of the jar.
     * 
     * @param  path the relative path inside of the jar
     * 
     * @return      the external data source of the resource
     */
    DataSource getSourceAsExternal(final String path);

}
