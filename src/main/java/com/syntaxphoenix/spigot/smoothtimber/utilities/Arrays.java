package com.syntaxphoenix.spigot.smoothtimber.utilities;

import java.util.function.IntFunction;

public abstract class Arrays {

    public static <E> E[] subArray(final IntFunction<E[]> function, final E[] args, final int index) {
        if (index < 0 || index >= args.length) {
            return function.apply(0);
        }
        final int length = args.length - index;
        final E[] output = function.apply(length);
        System.arraycopy(args, index, output, 0, length);
        return output;
    }

}
