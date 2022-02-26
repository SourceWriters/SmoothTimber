package net.sourcewriters.smoothtimber.api.data;

import java.util.function.Function;

public abstract class AbstractDataAdapter<P, C, B> implements IDataAdapter<P, C, B> {

    private final Function<P, C> builder;
    private final Function<C, P> extractor;

    private final Class<P> primitiveType;
    private final Class<C> complexType;

    public AbstractDataAdapter(final Class<P> primitiveType, final Class<C> complexType, final Function<P, C> builder,
        final Function<C, P> extractor) {
        this.primitiveType = primitiveType;
        this.complexType = complexType;
        this.builder = builder;
        this.extractor = extractor;
    }

    @Override
    public abstract Class<B> getBaseType();

    @Override
    public Class<P> getPrimitiveType() {
        return primitiveType;
    }

    @Override
    public Class<C> getComplexType() {
        return complexType;
    }

    @Override
    public P extract(final B input) {
        return complexType.isInstance(input) ? extractor.apply(complexType.cast(input)) : null;
    }

    @Override
    public C build(final Object input) {
        return primitiveType.isInstance(input) ? builder.apply(primitiveType.cast(input)) : null;
    }

    @Override
    public boolean isInstance(final B base) {
        return complexType.isInstance(base);
    }

}
