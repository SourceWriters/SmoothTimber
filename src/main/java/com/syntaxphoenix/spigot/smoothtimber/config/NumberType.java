package com.syntaxphoenix.spigot.smoothtimber.config;

import java.util.function.Function;

public final class NumberType<E extends Number> {

    public static final NumberType<Double> DOUBLE = new NumberType<>(double.class, Double.class, Number::doubleValue);
    public static final NumberType<Float> FLOAT = new NumberType<>(float.class, Float.class, Number::floatValue);
    public static final NumberType<Long> LONG = new NumberType<>(long.class, Long.class, Number::longValue);
    public static final NumberType<Integer> INT = new NumberType<>(int.class, Integer.class, Number::intValue);
    public static final NumberType<Short> SHORT = new NumberType<>(short.class, Short.class, Number::shortValue);
    public static final NumberType<Byte> BYTE = new NumberType<>(byte.class, Byte.class, Number::byteValue);

    private final Class<?> primitive;
    private final Class<E> complex;
    private final Function<Number, E> function;

    private NumberType(Class<?> primitive, Class<E> complex, Function<Number, E> function) {
        this.primitive = primitive;
        this.complex = complex;
        this.function = function;
    }

    public Class<?> getPrimitive() {
        return primitive;
    }

    public Class<E> getComplex() {
        return complex;
    }

    public E get(Number number) {
        return function.apply(number);
    }

    public static <N extends Number> N get(Class<N> clazz, Number input) {
        NumberType<N> type = find(clazz);
        if (type == null) {
            return null;
        }
        return type.get(input);
    }

    @SuppressWarnings("unchecked")
    public static <N extends Number> NumberType<N> find(Class<N> clazz) {
        for (NumberType<?> type : values()) {
            if (type.primitive == clazz || type.complex == clazz) {
                return (NumberType<N>) type;
            }
        }
        return null;
    }

    public static NumberType<?>[] values() {
        return new NumberType<?>[] {
            DOUBLE,
            FLOAT,
            LONG,
            INT,
            SHORT,
            BYTE
        };
    }

}
