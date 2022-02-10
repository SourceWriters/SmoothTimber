package net.sourcewriters.smoothtimber.api.util.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class DataSource {
    
    public abstract boolean exists();

    public abstract Object getSource();

    public abstract InputStream openStream() throws IOException;
    
    public BufferedReader openReader() throws IOException {
        return new BufferedReader(new InputStreamReader(openStream()));
    }
    
}
