package net.sourcewriters.smoothtimber.api.platform.world.data;

import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public interface IPlatformData {

    boolean isPersistent();

    IPlatformData newData();

    void set(ResourceKey key, byte value);

    void set(ResourceKey key, short value);

    void set(ResourceKey key, int value);

    void set(ResourceKey key, long value);

    void set(ResourceKey key, float value);

    void set(ResourceKey key, double value);

    void set(ResourceKey key, String value);

    void set(ResourceKey key, byte[] array);

    void set(ResourceKey key, int[] array);

    void set(ResourceKey key, long[] array);

    void set(ResourceKey key, IPlatformData data);

    byte getOrDefault(ResourceKey key, byte value);

    short getOrDefault(ResourceKey key, short value);

    int getOrDefault(ResourceKey key, int value);

    long getOrDefault(ResourceKey key, long value);

    float getOrDefault(ResourceKey key, float value);

    double getOrDefault(ResourceKey key, double value);

    String getOrDefault(ResourceKey key, String value);

    byte[] getOrDefault(ResourceKey key, byte[] value);

    int[] getOrDefault(ResourceKey key, int[] value);

    long[] getOrDefault(ResourceKey key, long[] value);

    byte getByte(ResourceKey key);

    short getShort(ResourceKey key);

    int getInt(ResourceKey key);

    long getLong(ResourceKey key);

    float getFloat(ResourceKey key);

    double getDouble(ResourceKey key);

    String getString(ResourceKey key);

    byte[] getByteArray(ResourceKey key);

    int[] getIntArray(ResourceKey key);

    long[] getLongArray(ResourceKey key);

    IPlatformData getData(ResourceKey key);

    IPlatformData getDataOrNew(ResourceKey key);

    boolean hasByte(ResourceKey key);

    boolean hasShort(ResourceKey key);

    boolean hasInt(ResourceKey key);

    boolean hasLong(ResourceKey key);

    boolean hasFloat(ResourceKey key);

    boolean hasDouble(ResourceKey key);

    boolean hasString(ResourceKey key);

    boolean hasByteArray(ResourceKey key);

    boolean hasIntArray(ResourceKey key);

    boolean hasLongArray(ResourceKey key);

    boolean hasData(ResourceKey key);

}
