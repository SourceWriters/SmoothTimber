package net.sourcewriters.smoothtimber.api.data;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public abstract class AbstractDataContainer<B> implements IDataContainer {

    protected final IDataAdapterRegistry<B> registry;

    public AbstractDataContainer(IDataAdapterRegistry<B> registry) {
        this.registry = registry;
    }

    @Override
    public IDataAdapterRegistry<B> getRegistry() {
        return registry;
    }

    @Override
    public Object get(String key) {
        B raw = getRaw(key);
        if (raw == null) {
            return raw;
        }
        return registry.extract(raw);
    }

    @Override
    public <E> E get(String key, IDataType<?, E> type) {
        Object value = registry.getBase().isAssignableFrom(type.getPrimitive()) ? getRaw(key) : get(key);
        if (value == null || !type.isPrimitive(value)) {
            if (Number.class.isAssignableFrom(type.getComplex())) {
                return NumberConversion.convert(0, type.getComplex());
            }
            return null;
        }
        E output = type.fromPrimitiveObj(getContext(), value);
        if (output == null && Number.class.isAssignableFrom(type.getComplex())) {
            return NumberConversion.convert(0, type.getComplex());
        }
        return output;
    }

    @Override
    public boolean has(String key, IDataType<?, ?> type) {
        if (!has(key)) {
            return false;
        }
        Object value = registry.getBase().isAssignableFrom(type.getPrimitive()) ? getRaw(key) : get(key);
        return value != null && type.isPrimitive(value);
    }

    @Override
    public <V, E> void set(String key, E value, IDataType<V, E> type) {
        set(key, registry.wrap(type.toPrimitive(getContext(), value)));
    }

    /*
     * Key conversion
     */

    @Override
    public Object get(ResourceKey key) {
        return get(key.toString());
    }

    @Override
    public <E> E get(ResourceKey key, IDataType<?, E> type) {
        return get(key.toString(), type);
    }

    @Override
    public <V, E> void set(ResourceKey key, E value, IDataType<V, E> type) {
        set(key.toString(), value, type);
    }

    @Override
    public boolean has(ResourceKey key) {
        return has(key.toString());
    }

    @Override
    public boolean has(ResourceKey key, IDataType<?, ?> type) {
        return has(key.toString(), type);
    }

    @Override
    public boolean remove(ResourceKey key) {
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

    public B getRaw(ResourceKey key) {
        return getRaw(key.toString());
    }

    public abstract void set(String key, B value);

    public void set(ResourceKey key, B value) {
        set(key.toString(), value);
    }

}
