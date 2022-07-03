package net.sourcewriters.smoothtimber.api.util;

import java.io.File;
import java.nio.file.Path;

import net.sourcewriters.smoothtimber.api.util.source.DataSource;

public interface IResource {

    /**
     * Get jar root path
     * 
     * @return the root path
     */
    Path getInternalRootPath();

    /**
     * Get path of resource inside of jar
     * 
     * @param  path the relative path inside of the jar
     * 
     * @return      the path of the resource
     */
    Path getInternalPath(final String path);

    /**
     * Get data source of resource inside of jar
     * 
     * @param  path the relative path inside of the jar
     * 
     * @return      the data source of the resource
     */
    DataSource getInternalSource(final String path);

    /**
     * Copies a resource inside of the jar into the module specific data folder and
     * returns the path to the copied resource outside of the jar.
     * 
     * @param  path the relative path inside of the jar
     * 
     * @return      the external path of the resource
     */
    Path getInternalPathAsExternal(final String path);

    /**
     * Copies a resource inside of the jar into the module specific data folder and
     * returns the data source to the copied resource outside of the jar.
     * 
     * @param  path the relative path inside of the jar
     * 
     * @return      the external data source of the resource
     */
    DataSource getInternalSourceAsExternal(final String path);

    /**
     * Gets the data root
     * 
     * @return the data root
     */
    File getExternalRoot();

    /**
     * Gets the file of a resource in the data root
     * 
     * @param  path the relative path inside of the data root
     * 
     * @return      the file of the resource
     */
    File getExternalFile(final String path);

    /**
     * Gets the path of a resource in the data root
     * 
     * @param  path the relative path inside of the data root
     * 
     * @return      the path of the resource
     */
    Path getExternalPath(final String path);

    /**
     * Gets the data source of a resource in the data root
     * 
     * @param  path the relative path inside of the data root
     * 
     * @return      the data source of the resource
     */
    DataSource getExternalSource(final String path);

}
