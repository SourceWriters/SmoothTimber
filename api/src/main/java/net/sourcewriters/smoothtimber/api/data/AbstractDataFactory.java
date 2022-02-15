package net.sourcewriters.smoothtimber.api.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractDataFactory<B> implements IDataFactory<B> {

    protected final IDataAdapterRegistry<B> registry;

    public AbstractDataFactory(IDataAdapterRegistry<B> registry) {
        this.registry = registry;
    }

    @Override
    public IDataAdapterRegistry<B> getRegistry() {
        return registry;
    }

    @Override
    public abstract AbstractDataFactory<B> toFile(IDataContainer container, File file) throws IOException;

    @Override
    public abstract AbstractDataFactory<B> toStream(IDataContainer container, OutputStream stream) throws IOException;

    @Override
    public abstract AbstractDataFactory<B> toString(IDataContainer container, StringBuilder builder) throws IOException;

    @Override
    public abstract AbstractDataFactory<B> fromFile(IDataContainer container, File file) throws IOException;

    @Override
    public abstract AbstractDataFactory<B> fromStream(IDataContainer container, InputStream stream) throws IOException;

    @Override
    public abstract AbstractDataFactory<B> fromString(IDataContainer container, String string) throws IOException;

}
