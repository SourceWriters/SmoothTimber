package net.sourcewriters.smoothtimber.api.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.function.Function;

public final class NumberConversion {

    private static final HashMap<Class<?>, Function<Number, Number>> MAP = new HashMap<>();

    static {
        MAP.put(Byte.class, Number::byteValue);
        MAP.put(Short.class, Number::shortValue);
        MAP.put(Integer.class, Number::intValue);
        MAP.put(Float.class, Number::floatValue);
        MAP.put(Long.class, Number::longValue);
        MAP.put(Double.class, Number::doubleValue);
        MAP.put(BigInteger.class, number -> BigInteger.valueOf(number.longValue()));
        MAP.put(BigDecimal.class, number -> BigDecimal.valueOf(number.doubleValue()));
    }

    private NumberConversion() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <E> E convert(final Number number, final Class<E> clazz) {
        final Function<Number, Number> mapper = MAP.get(clazz);
        if (mapper == null) {
            return null;
        }
        return clazz.cast(mapper.apply(number));
    }

}
