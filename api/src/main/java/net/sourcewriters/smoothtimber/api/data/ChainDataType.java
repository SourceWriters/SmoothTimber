package net.sourcewriters.smoothtimber.api.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public class ChainDataType<P extends Object, C extends Object> implements IDataType<P, C> {

    private final ArrayList<IDataType<?, ?>> types = new ArrayList<>();

    /*
     * Contains
     */

    public boolean has(IDataType<?, ?> type) {
        return hasExact(type) || hasComplex(type.getComplex());
    }

    public boolean hasExact(IDataType<?, ?> type) {
        return types.contains(type);
    }

    public boolean hasComplex(Class<?> complex) {
        return types.stream().anyMatch(type -> Objects.equals(type.getComplex(), complex));
    }

    /*
     * Add
     */

    public ChainDataType<P, C> add(IDataType<?, ?> type) {
        if (type == null) {
            return this;
        }
        if (has(type)) {
            return this;
        }
        types.add(type);
        return this;
    }

    public ChainDataType<P, C> addAll(IDataType<?, ?>... types) {
        if (types == null) {
            return this;
        }
        for (int index = 0; index < types.length; index++) {
            add(types[index]);
        }
        return this;
    }

    public ChainDataType<P, C> addAll(Iterable<IDataType<?, ?>> types) {
        if (types == null) {
            return this;
        }
        return addAll(types.iterator());
    }

    public ChainDataType<P, C> addAll(Iterator<IDataType<?, ?>> types) {
        if (types == null) {
            return this;
        }
        while (types.hasNext()) {
            add(types.next());
        }
        return this;
    }

    /*
     * Remove
     */

    public ChainDataType<P, C> remove(IDataType<?, ?> type) {
        types.remove(type);
        return this;
    }

    public ChainDataType<P, C> removeAll(IDataType<?, ?>... types) {
        if (types == null) {
            return this;
        }
        for (int index = 0; index < types.length; index++) {
            remove(types[index]);
        }
        return this;
    }

    public ChainDataType<P, C> removeAll(Iterable<IDataType<?, ?>> types) {
        if (types == null) {
            return this;
        }
        return removeAll(types.iterator());
    }

    public ChainDataType<P, C> removeAll(Iterator<IDataType<?, ?>> types) {
        if (types == null) {
            return this;
        }
        while (types.hasNext()) {
            remove(types.next());
        }
        return this;
    }

    /*
     * Get
     */

    public IDataType<?, ?> get(int index) {
        return types.get(index);
    }

    public IDataType<?, ?>[] get(int start, int length) {
        return types.subList(start, start + length).toArray(new IDataType[0]);
    }

    /*
     * Other
     */

    public int size() {
        return types.size();
    }

    public boolean isEmpty() {
        return types.isEmpty();
    }

    public ChainDataType<P, C> clear() {
        types.clear();
        return this;
    }

    /*
     * Cast
     */

    @SuppressWarnings("unchecked")
    public <T> Optional<ChainDataType<P, T>> asComplex(Class<T> complex) {
        return Optional.ofNullable(Objects.equals(getComplex(), complex) ? (ChainDataType<P, T>) this : null);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<ChainDataType<T, C>> asPrimitive(Class<T> primitive) {
        return Optional.ofNullable(Objects.equals(getPrimitive(), primitive) ? (ChainDataType<T, C>) this : null);
    }

    /*
     * DataType
     */

    @SuppressWarnings("unchecked")
    @Override
    public Class<C> getComplex() {
        return isEmpty() ? null : (Class<C>) types.get(size() - 1).getComplex();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<P> getPrimitive() {
        return isEmpty() ? null : (Class<P>) types.get(0).getPrimitive();
    }

    @SuppressWarnings("unchecked")
    @Override
    public C fromPrimitive(IDataAdapterContext context, P primitive) {
        Object output = primitive;
        for (int index = size() - 1; index > -1; index--) {
            if (output == null) {
                return null;
            }
            output = types.get(index).fromPrimitiveObj(context, output);
        }
        return isComplex(output) ? (C) output : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public P toPrimitive(IDataAdapterContext context, C complex) {
        Object output = complex;
        for (int index = size() - 1; index > -1; index--) {
            if (output == null) {
                return null;
            }
            output = types.get(index).toPrimitiveObj(context, output);
        }
        return isPrimitive(output) ? (P) output : null;
    }

}
