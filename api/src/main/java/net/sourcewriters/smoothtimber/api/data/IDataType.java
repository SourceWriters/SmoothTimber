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

    public static final IDataType<Byte, Byte> BYTE = of(Byte.class);
    public static final IDataType<Short, Short> SHORT = of(Short.class);
    public static final IDataType<Integer, Integer> INTEGER = of(Integer.class);
    public static final IDataType<Long, Long> LONG = of(Long.class);

    public static final IDataType<Float, Float> FLOAT = of(Float.class);
    public static final IDataType<Double, Double> DOUBLE = of(Double.class);

    public static final IDataType<BigInteger, BigInteger> BIG_INTEGER = of(BigInteger.class);
    public static final IDataType<BigDecimal, BigDecimal> BIG_DECIMAL = of(BigDecimal.class);

    public static final IDataType<Boolean, Boolean> BOOLEAN = of(Boolean.class);

    public static final IDataType<String, String> STRING = of(String.class);

    public static final IDataType<int[], int[]> INT_ARRAY = of(int[].class);
    public static final IDataType<byte[], byte[]> BYTE_ARRAY = of(byte[].class);
    public static final IDataType<long[], long[]> LONG_ARRAY = of(long[].class);

    public static final IDataType<IDataContainer, IDataContainer> CONTAINER = of(IDataContainer.class);
    public static final IDataType<IDataContainer[], IDataContainer[]> CONTAINER_ARRAY = of(IDataContainer[].class);

    public static final IDataType<byte[], UUID> UUID = of(byte[].class, UUID.class, array -> {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        long mostSignificant = buffer.getLong();
        long leastSignificant = buffer.getLong();
        return new UUID(mostSignificant, leastSignificant);
    }, uniqueId -> {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uniqueId.getMostSignificantBits());
        buffer.putLong(uniqueId.getLeastSignificantBits());
        return buffer.array();
    });

    public static final IDataType<?, ?>[] PRIMITIVES = new IDataType<?, ?>[] {
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

    public static Optional<IDataType<?, ?>> getPrimitiveType(Object object) {
        Class<?> clazz = Primitives.fromPrimitive(object.getClass());
        return Arrays.stream(PRIMITIVES).filter(type -> Objects.equals(clazz, type.getComplex())).findFirst();
    }

    public static boolean isPrimitiveType(Object object) {
        Class<?> clazz = Primitives.fromPrimitive(object.getClass());
        return Arrays.stream(PRIMITIVES).anyMatch(type -> Objects.equals(clazz, type.getComplex()));
    }

    public default boolean isComplex(Object object) {
        if (object == null) {
            return false;
        }
        return getComplex().isAssignableFrom(Primitives.fromPrimitive(object.getClass()));
    }

    public default boolean isPrimitive(Object object) {
        if (object == null) {
            return false;
        }
        return getPrimitive().isAssignableFrom(Primitives.fromPrimitive(object.getClass()));
    }

    public Class<C> getComplex();

    public Class<P> getPrimitive();

    public P toPrimitive(IDataAdapterContext context, C complex);

    public C fromPrimitive(IDataAdapterContext context, P primitive);

    @SuppressWarnings("unchecked")
    public default P toPrimitiveObj(IDataAdapterContext context, Object complex) {
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
    public default C fromPrimitiveObj(IDataAdapterContext context, Object primitive) {
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

    public static <P> IDataType<P, P> of(Class<P> type) {
        return new DefaultDataType<>(type, type, (value, context) -> value, (value, context) -> value);
    }

    public static <P, C> IDataType<P, C> of(Class<P> primitive, Class<C> complex, Function<P, C> from, Function<C, P> to) {
        return new DefaultDataType<>(primitive, complex, (value, context) -> from.apply(value), (value, context) -> to.apply(value));
    }

    public static <P, C> IDataType<P, C> of(Class<P> primitive, Class<C> complex, BiFunction<P, IDataAdapterContext, C> from,
        BiFunction<C, IDataAdapterContext, P> to) {
        return new DefaultDataType<>(primitive, complex, from, to);
    }

}