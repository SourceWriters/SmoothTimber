package net.sourcewriters.smoothtimber.api.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

public interface IDataType<P extends Object, C extends Object> {

    IDataType<Byte, Byte> BYTE = of(Byte.class);
    IDataType<Short, Short> SHORT = of(Short.class);
    IDataType<Integer, Integer> INTEGER = of(Integer.class);
    IDataType<Long, Long> LONG = of(Long.class);

    IDataType<Float, Float> FLOAT = of(Float.class);
    IDataType<Double, Double> DOUBLE = of(Double.class);

    IDataType<BigInteger, BigInteger> BIG_INTEGER = of(BigInteger.class);
    IDataType<BigDecimal, BigDecimal> BIG_DECIMAL = of(BigDecimal.class);

    IDataType<Boolean, Boolean> BOOLEAN = of(Boolean.class);

    IDataType<String, String> STRING = of(String.class);

    IDataType<int[], int[]> INT_ARRAY = of(int[].class);
    IDataType<byte[], byte[]> BYTE_ARRAY = of(byte[].class);
    IDataType<long[], long[]> LONG_ARRAY = of(long[].class);

    IDataType<IDataContainer, IDataContainer> CONTAINER = of(IDataContainer.class);
    IDataType<IDataContainer[], IDataContainer[]> CONTAINER_ARRAY = of(IDataContainer[].class);

    IDataType<byte[], UUID> UUID = of(byte[].class, UUID.class, array -> {
        final ByteBuffer buffer = ByteBuffer.wrap(array);
        final long mostSignificant = buffer.getLong();
        final long leastSignificant = buffer.getLong();
        return new UUID(mostSignificant, leastSignificant);
    }, uniqueId -> {
        final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uniqueId.getMostSignificantBits());
        buffer.putLong(uniqueId.getLeastSignificantBits());
        return buffer.array();
    });

    IDataType<?, ?>[] PRIMITIVES = {
        BYTE,
        SHORT,
        INTEGER,
        LONG,
        FLOAT,
        DOUBLE,
        BIG_INTEGER,
        BIG_DECIMAL,
        BOOLEAN,
        STRING,
        INT_ARRAY,
        BYTE_ARRAY,
        LONG_ARRAY,
        CONTAINER,
        CONTAINER_ARRAY
    };

    static Optional<IDataType<?, ?>> getPrimitiveType(final Object object) {
        final Class<?> clazz = Primitives.fromPrimitive(object.getClass());
        return Arrays.stream(PRIMITIVES).filter(type -> Objects.equals(clazz, type.getComplex())).findFirst();
    }

    static boolean isPrimitiveType(final Object object) {
        final Class<?> clazz = Primitives.fromPrimitive(object.getClass());
        return Arrays.stream(PRIMITIVES).anyMatch(type -> Objects.equals(clazz, type.getComplex()));
    }

    default boolean isComplex(final Object object) {
        if (object == null) {
            return false;
        }
        return getComplex().isAssignableFrom(Primitives.fromPrimitive(object.getClass()));
    }

    default boolean isPrimitive(final Object object) {
        if (object == null) {
            return false;
        }
        return getPrimitive().isAssignableFrom(Primitives.fromPrimitive(object.getClass()));
    }

    Class<C> getComplex();

    Class<P> getPrimitive();

    P toPrimitive(IDataAdapterContext context, C complex);

    C fromPrimitive(IDataAdapterContext context, P primitive);

    @SuppressWarnings("unchecked")
    default P toPrimitiveObj(final IDataAdapterContext context, final Object complex) {
        if (complex == null) {
            return null;
        }
        if (isComplex(complex)) {
            return toPrimitive(context, (C) complex);
        }
        if (isPrimitive(complex)) {
            return (P) complex;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    default C fromPrimitiveObj(final IDataAdapterContext context, final Object primitive) {
        if (primitive == null) {
            return null;
        }
        if (isPrimitive(primitive)) {
            return fromPrimitive(context, (P) primitive);
        }
        if (isComplex(primitive)) {
            return (C) primitive;
        }
        return null;
    }

    static <P> IDataType<P, P> of(final Class<P> type) {
        return new DefaultDataType<>(type, type, (value, context) -> value, (value, context) -> value);
    }

    static <P, C> IDataType<P, C> of(final Class<P> primitive, final Class<C> complex, final Function<P, C> from, final Function<C, P> to) {
        return new DefaultDataType<>(primitive, complex, (value, context) -> from.apply(value), (value, context) -> to.apply(value));
    }

    static <P, C> IDataType<P, C> of(final Class<P> primitive, final Class<C> complex, final BiFunction<P, IDataAdapterContext, C> from,
        final BiFunction<C, IDataAdapterContext, P> to) {
        return new DefaultDataType<>(primitive, complex, from, to);
    }

}