package net.sourcewriters.smoothtimber.api.util.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class DataSource {

    /**
     * Check if the source exists
     * 
     * @return if the source exists or not
     */
    public abstract boolean exists();

    /**
     * Get the source object
     * 
     * @return the source object
     */
    public abstract Object getSource();

    /**
     * Open a readable stream for the source
     * 
     * @return             the input stream
     * 
     * @throws IOException if an I/O error occurs
     */
    public abstract InputStream openStream() throws IOException;

    /**
     * Open a buffered reader for the source
     * 
     * @return             the buffered reader
     * 
     * @throws IOException if an I/O error occurs
     */
    public BufferedReader openReader() throws IOException {
        return new BufferedReader(new InputStreamReader(openStream()));
    }

}
