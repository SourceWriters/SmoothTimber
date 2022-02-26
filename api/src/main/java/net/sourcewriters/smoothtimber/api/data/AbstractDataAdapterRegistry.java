package net.sourcewriters.smoothtimber.api.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractDataAdapterRegistry<B> implements IDataAdapterRegistry<B> {

    protected final List<IDataAdapter<?, ? extends B, B>> adapters = Collections.synchronizedList(new ArrayList<>());

    protected abstract <P, C extends B> IDataAdapter<P, C, B> build(Class<?> clazz);

    public abstract <P, C extends B> IDataAdapter<P, C, B> create(Class<P> primitiveType, Class<C> complexType, Function<P, C> builder,
        Function<C, P> extractor);

    public AbstractDataAdapterRegistry() {
        for (final IDataType<?, ?> type : IDataType.PRIMITIVES) {
            adapters.add(build(type.getPrimitive()));
        }
    }

    @Override
    public B wrap(final Object value) {
        if (value == null) {
            return null;
        }
        final IDataAdapter<?, ? extends B, B> adapter = find(value.getClass(), IDataAdapter::getPrimitiveType);
        if (adapter == null) {
            return null;
        }
        return adapter.getPrimitiveType().isInstance(value) ? adapter.build(value) : null;
    }

    @Override
    public Object extract(final B base) {
        if (base == null) {
            return null;
        }
        final IDataAdapter<?, ? extends B, B> adapter = find(base.getClass(), IDataAdapter::getComplexType);
        return adapter == null ? null : adapter.extract(base);
    }

    @Override
    public boolean has(final Class<?> clazz) {
        return adapters.stream()
            .anyMatch(adapter -> Objects.equals(clazz, adapter.getPrimitiveType()) || Objects.equals(clazz, adapter.getComplexType()));
    }

    private IDataAdapter<?, ? extends B, B> find(final Class<?> clazz, final Function<IDataAdapter<?, ? extends B, B>, Class<?>> mapper) {
        return adapters.stream().filter(adapter -> mapper.apply(adapter).isAssignableFrom(clazz)).findAny().orElseGet(() -> build(clazz));
    }

}
