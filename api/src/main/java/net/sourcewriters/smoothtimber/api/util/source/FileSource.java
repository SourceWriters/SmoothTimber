package net.sourcewriters.smoothtimber.api.util.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class FileSource extends DataSource {

    private final File file;

    public FileSource(final File file) {
        this.file = file;
    }

    @Override
    public boolean exists() {
        return file.exists() && file.isFile();
    }

    @Override
    public File getSource() {
        return file;
    }

    @Override
    public InputStream openStream() throws IOException {
        return new FileInputStream(file);
    }

}
