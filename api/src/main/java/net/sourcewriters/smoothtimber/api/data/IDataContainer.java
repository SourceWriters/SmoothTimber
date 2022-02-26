package net.sourcewriters.smoothtimber.api.data;

import java.util.Set;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public interface IDataContainer {
    
    IDataAdapterRegistry<?> getRegistry();
    
    IDataAdapterContext getContext();
    
    boolean has(String key);
    
    boolean has(ResourceKey key);
    
    boolean has(String key, IDataType<?, ?> type);
    
    boolean has(ResourceKey key, IDataType<?, ?> type);
    
    Object get(String key);
    
    Object get(ResourceKey key);

    <E> E get(String key, IDataType<?, E> type);

    <E> E get(ResourceKey key, IDataType<?, E> type);
    
    boolean remove(String key);
    
    boolean remove(ResourceKey key);
    
    <V, E> void set(String key, E value, IDataType<V, E> type);
    
    <V, E> void set(ResourceKey key, E value, IDataType<V, E> type);

    Set<String> getKeyspaces();

    ResourceKey[] getKeys();

    boolean isEmpty();

    int size();

}
