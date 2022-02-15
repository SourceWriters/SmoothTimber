package net.sourcewriters.smoothtimber.api.data;

import java.util.function.BiFunction;

class DefaultDataType<P, C, B> implements IDataType<P, C> {

    private final Class<P> primitiveType;
    private final Class<C> complexType;

    private final BiFunction<P, IDataAdapterContext, C> fromPrimitive;
    private final BiFunction<C, IDataAdapterContext, P> toPrimitive;

    DefaultDataType(Class<P> primitiveType, Class<C> complexType, BiFunction<P, IDataAdapterContext, C> fromPrimitive,
        BiFunction<C, IDataAdapterContext, P> toPrimitive) {
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
    public P toPrimitive(IDataAdapterContext context, C complex) {
        return toPrimitive.apply(complex, context);
    }

    @Override
    public C fromPrimitive(IDataAdapterContext context, P primitive) {
        return fromPrimitive.apply(primitive, context);
    }

}