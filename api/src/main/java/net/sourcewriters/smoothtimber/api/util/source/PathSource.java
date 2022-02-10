package net.sourcewriters.smoothtimber.api.util.source;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class PathSource extends DataSource {

    private final Path path;

    public PathSource(Path path) {
        this.path = path;
    }

    @Override
    public boolean exists() {
        return Files.exists(path);
    }

    @Override
    public Path getSource() {
        return path;
    }

    @Override
    public InputStream openStream() throws IOException {
        return path.getFileSystem().provider().newInputStream(path, StandardOpenOption.READ);
    }

}
