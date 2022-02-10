package net.sourcewriters.smoothtimber.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.file.PathUtils;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;
import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;
import com.syntaxphoenix.syntaxapi.utils.java.Files;
import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlugin;
import net.sourcewriters.smoothtimber.api.util.IResource;
import net.sourcewriters.smoothtimber.api.util.source.DataSource;
import net.sourcewriters.smoothtimber.api.util.source.PathSource;

public final class ResourceImpl implements IResource {

    private final File folder;
    private final ILogger logger;
    private final URI jarUri;
    private final boolean jarFile;

    private final Container<Path> root = Container.of();

    public ResourceImpl(final ILogger logger, final ISmoothTimberPlugin plugin) {
        this.logger = logger;
        this.folder = plugin.getSystemFolder();
        this.jarFile = plugin.getSystemJar().getName().endsWith(".jar");
        this.jarUri = buildUri(plugin.getSystemJar());
    }

    public ResourceImpl(final SmoothTimberModule module) {
        this.logger = module.getLogger();
        this.folder = module.getDataLocation();
        this.jarFile = module.getWrapper().getFile().getName().endsWith(".jar");
        this.jarUri = buildUri(module.getWrapper().getFile());
    }

    @Override
    public Path getRootPath() {
        if (root.isPresent()) {
            return root.get();
        }
        try {
            return root.replace(jarFile ? getPathFor(jarUri, "/") : Paths.get(jarUri).resolve("classes")).lock().get();
        } catch (IOException e) {
            logger.log(LogTypeId.ERROR, "Failed to retrieve resource root!");
            logger.log(LogTypeId.ERROR, e);
            return null;
        }
    }

    @Override
    public Path getPath(String path) {
        return getRootPath().resolveSibling(path);
    }

    @Override
    public Path getPathAsExternal(String path) {
        final File target = new File(folder, path);
        try {
            final Path root = getRootPath().resolveSibling(path);
            if (root == null) {
                return target.toPath();
            }
            if (PathUtils.isDirectory(root)) {
                return createDirectoryPath(root, target);
            }
            return createFilePath(root, target);
        } catch (final Exception exp) {
            logger.log(LogTypeId.ERROR, "Failed to retrieve resource '" + path + "'!");
            logger.log(LogTypeId.ERROR, Exceptions.stackTraceToString(exp));
            return target.toPath();
        }
    }

    @Override
    public DataSource getSource(String path) {
        return new PathSource(getPath(path));
    }

    @Override
    public DataSource getSourceAsExternal(String path) {
        Path pathObj = getPath(path);
        if (PathUtils.isDirectory(pathObj)) {
            return null;
        }
        try {
            return new PathSource(createFilePath(pathObj, new File(folder, path)));
        } catch (final Exception exp) {
            logger.log(LogTypeId.ERROR, "Failed to retrieve resource '" + path + "'!");
            logger.log(LogTypeId.ERROR, Exceptions.stackTraceToString(exp));
            return null;
        }
    }

    /*
     * Utils
     */

    private URI buildUri(File jarFile) {
        try {
            return new URI(("jar:file:/" + jarFile.getAbsolutePath().replace('\\', '/').replace(" ", "%20") + "!/").replace("//", "/"));
        } catch (URISyntaxException e) {
            logger.log(e);
            return jarFile.toURI();
        }
    }

    private Path getPathFor(final URI uri, final String path) throws IOException {
        try {
            return FileSystems.getFileSystem(uri).getPath(path);
        } catch (final Exception exp) {
            return FileSystems.newFileSystem(uri, Collections.emptyMap()).getPath(path);
        }
    }

    private Path createDirectoryPath(final Path path, final File target) throws Exception {
        if (target.exists()) {
            return target.toPath();
        }
        Files.createFolder(target);
        try (Stream<Path> walk = java.nio.file.Files.walk(path, 1)) {
            for (final Iterator<Path> iterator = walk.iterator(); iterator.hasNext();) {
                final Path next = iterator.next();
                if (next == path) {
                    continue;
                }
                final File nextTarget = new File(target, next.getName(next.getNameCount() - 1).toString());
                if (PathUtils.isDirectory(next)) {
                    createDirectoryPath(next, nextTarget);
                    continue;
                }
                createFilePath(next, nextTarget);
            }
        }
        return target.toPath();
    }

    private Path createFilePath(final Path path, final File target) throws Exception {
        if (target.exists()) {
            return target.toPath();
        }
        Files.createFile(target);
        try (InputStream input = path.getFileSystem().provider().newInputStream(path, StandardOpenOption.READ)) {
            try (FileOutputStream output = new FileOutputStream(target)) {
                IOUtils.copy(input, output);
            }
        }
        return target.toPath();
    }

}
