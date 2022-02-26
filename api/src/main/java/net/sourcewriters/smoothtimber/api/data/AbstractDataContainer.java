package net.sourcewriters.smoothtimber.api.data;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public abstract class AbstractDataContainer<B> implements IDataContainer {

    protected final IDataAdapterRegistry<B> registry;

    public AbstractDataContainer(final IDataAdapterRegistry<B> registry) {
        this.registry = registry;
    }

    @Override
    public IDataAdapterRegistry<B> getRegistry() {
        return registry;
    }

    @Override
    public Object get(final String key) {
        final B raw = getRaw(key);
        if (raw == null) {
            return raw;
        }
        return registry.extract(raw);
    }

    @Override
    public <E> E get(final String key, final IDataType<?, E> type) {
        final Object value = registry.getBase().isAssignableFrom(type.getPrimitive()) ? getRaw(key) : get(key);
        if (value == null || !type.isPrimitive(value)) {
            if (Number.class.isAssignableFrom(type.getComplex())) {
                return NumberConversion.convert(0, type.getComplex());
            }
            return null;
        }
        final E output = type.fromPrimitiveObj(getContext(), value);
        if (output == null && Number.class.isAssignableFrom(type.getComplex())) {
            return NumberConversion.convert(0, type.getComplex());
        }
        return output;
    }

    @Override
    public boolean has(final String key, final IDataType<?, ?> type) {
        if (!has(key)) {
            return false;
        }
        final Object value = registry.getBase().isAssignableFrom(type.getPrimitive()) ? getRaw(key) : get(key);
        return value != null && type.isPrimitive(value);
    }

    @Override
    public <V, E> void set(final String key, final E value, final IDataType<V, E> type) {
        set(key, registry.wrap(type.toPrimitive(getContext(), value)));
    }

    /*
     * Key conversion
     */

    @Override
    public Object get(final ResourceKey key) {
        return get(key.toString());
    }

    @Override
    public <E> E get(final ResourceKey key, final IDataType<?, E> type) {
        return get(key.toString(), type);
    }

    @Override
    public <V, E> void set(final ResourceKey key, final E value, final IDataType<V, E> type) {
        set(key.toString(), value, type);
    }

    @Override
    public boolean has(final ResourceKey key) {
        return has(key.toString());
    }

    @Override
    public boolean has(final ResourceKey key, final IDataType<?, ?> type) {
        return has(key.toString(), type);
    }

    @Override
    public boolean remove(final ResourceKey key) {
        return remove(key.toString());
    }

    @Override
    public ResourceKey[] getKeys() {
        return getKeyspaces().stream().map(ResourceKey::fromString).toArray(ResourceKey[]::new);
    }

    /*
     * Abstract
     */

    public abstract B getRaw(String key);

    public B getRaw(final ResourceKey key) {
        return getRaw(key.toString());
    }

    public abstract void set(String key, B value);

    public void set(final ResourceKey key, final B value) {
        set(key.toString(), value);
    }

}
