package net.sourcewriters.smoothtimber.api.data;

import java.util.function.Function;

public abstract class AbstractDataAdapter<P, C, B> implements IDataAdapter<P, C, B> {

    private final Function<P, C> builder;
    private final Function<C, P> extractor;

    private final Class<P> primitiveType;
    private final Class<C> complexType;

    public AbstractDataAdapter(Class<P> primitiveType, Class<C> complexType, Function<P, C> builder, Function<C, P> extractor) {
        this.primitiveType = primitiveType;
        this.complexType = complexType;
        this.builder = builder;
        this.extractor = extractor;
    }

    public abstract Class<B> getBaseType();

    @Override
    public Class<P> getPrimitiveType() {
        return primitiveType;
    }

    @Override
    public Class<C> getComplexType() {
        return complexType;
    }

    public P extract(B input) {
        return complexType.isInstance(input) ? extractor.apply(complexType.cast(input)) : null;
    }

    public C build(Object input) {
        return primitiveType.isInstance(input) ? builder.apply(primitiveType.cast(input)) : null;
    }

    public boolean isInstance(B base) {
        return complexType.isInstance(base);
    }

}
