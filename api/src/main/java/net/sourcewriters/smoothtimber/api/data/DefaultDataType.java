package net.sourcewriters.smoothtimber.api.data;

import java.util.function.BiFunction;

class DefaultDataType<P, C, B> implements IDataType<P, C> {

    private final Class<P> primitiveType;
    private final Class<C> complexType;

    private final BiFunction<P, IDataAdapterContext, C> fromPrimitive;
    private final BiFunction<C, IDataAdapterContext, P> toPrimitive;

    DefaultDataType(final Class<P> primitiveType, final Class<C> complexType, final BiFunction<P, IDataAdapterContext, C> fromPrimitive,
        final BiFunction<C, IDataAdapterContext, P> toPrimitive) {
        this.primitiveType = primitiveType;
        this.complexType = complexType;
        this.fromPrimitive = fromPrimitive;
        this.toPrimitive = toPrimitive;
    }

    @Override
    public Class<C> getComplex() {
        return this.complexType;
    }

    @Override
    public Class<P> getPrimitive() {
        return this.primitiveType;
    }

    @Override
    public P toPrimitive(final IDataAdapterContext context, final C complex) {
        return toPrimitive.apply(complex, context);
    }

    @Override
    public C fromPrimitive(final IDataAdapterContext context, final P primitive) {
        return fromPrimitive.apply(primitive, context);
    }

}