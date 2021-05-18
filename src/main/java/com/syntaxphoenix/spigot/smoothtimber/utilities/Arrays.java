package com.syntaxphoenix.spigot.smoothtimber.utilities;

import java.util.function.IntFunction;

public abstract class Arrays {

    public static <E> E[] subArray(IntFunction<E[]> function, E[] args, int index) {
        if (index < 0 || index >= args.length) {
            return function.apply(0);
        }
        int length = args.length - index;
        E[] output = function.apply(length);
        System.arraycopy(args, index, output, 0, length);
        return output;
    }

}
