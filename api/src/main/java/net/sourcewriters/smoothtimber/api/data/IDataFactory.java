package net.sourcewriters.smoothtimber.api.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IDataFactory<B> {

    IDataAdapterRegistry<B> getRegistry();

    IDataFactory<B> toFile(IDataContainer container, File file) throws IOException;

    IDataFactory<B> toStream(IDataContainer container, OutputStream stream) throws IOException;

    IDataFactory<B> toString(IDataContainer container, StringBuilder builder) throws IOException;

    IDataFactory<B> fromFile(IDataContainer container, File file) throws IOException;

    IDataFactory<B> fromStream(IDataContainer container, InputStream stream) throws IOException;

    IDataFactory<B> fromString(IDataContainer container, String string) throws IOException;

}
