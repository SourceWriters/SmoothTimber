package net.sourcewriters.smoothtimber.api.util;

import java.util.function.Function;
import java.util.function.Supplier;

public final class Tuple<F, S> {

    private final F first;
    private final S second;

    public Tuple(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    public <N> Tuple<N, S> mapFirst(final Function<F, N> mapper) {
        if (first == null) {
            return new Tuple<>(null, second);
        }
        return new Tuple<>(mapper.apply(first), second);
    }

    public <N> Tuple<F, N> mapSecond(final Function<S, N> mapper) {
        if (second == null) {
            return new Tuple<>(first, null);
        }
        return new Tuple<>(first, mapper.apply(second));
    }

    public F getFirst() {
        return first;
    }

    public F getFirstOrDefault(final F fallback) {
        if (first == null) {
            return fallback;
        }
        return first;
    }

    public F getFirstOrThrow(final Supplier<Throwable> supplier) throws Throwable {
        if (first == null) {
            throw supplier.get();
        }
        return first;
    }

    public S getSecond() {
        return second;
    }

    public S getSecondOrDefault(final S fallback) {
        if (second == null) {
            return fallback;
        }
        return second;
    }

    public S getSecondOrThrow(final Supplier<Throwable> supplier) throws Throwable {
        if (second == null) {
            throw supplier.get();
        }
        return second;
    }

    public static <F, S> Tuple<F, S> of(final F first, final S second) {
        return new Tuple<>(first, second);
    }

}
