package net.sourcewriters.smoothtimber.spigot.world.data;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

import net.sourcewriters.smoothtimber.api.platform.world.data.IPlatformData;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;

public final class SpigotMetaData implements IPlatformData {

    private final Metadatable data;
    private final Plugin plugin;

    public SpigotMetaData(Plugin plugin, Metadatable data) {
        this.data = data;
        this.plugin = plugin;
    }

    @Override
    public boolean isPersistent() {
        return false;
    }

    @Override
    public IPlatformData newData() {
        return new SpigotMemoryData();
    }

    private void set(String key, Object value) {
        data.setMetadata(key, new FixedMetadataValue(plugin, value));
    }

    private Object get(String key) {
        return data.getMetadata(key).stream().filter(value -> value.getOwningPlugin() == plugin).findFirst().map(value -> value.value())
            .orElse(null);
    }

    @Override
    public void set(ResourceKey key, byte value) {
        set(key.toResourceString(), value);
    }

    @Override
    public void set(ResourceKey key, short value) {
        set(key.toResourceString(), value);
    }

    @Override
    public void set(ResourceKey key, int value) {
        set(key.toResourceString(), value);
    }

    @Override
    public void set(ResourceKey key, long value) {
        set(key.toResourceString(), value);
    }

    @Override
    public void set(ResourceKey key, float value) {
        set(key.toResourceString(), value);
    }

    @Override
    public void set(ResourceKey key, double value) {
        set(key.toResourceString(), value);
    }

    @Override
    public void set(ResourceKey key, String value) {
        set(key.toResourceString(), value);
    }

    @Override
    public void set(ResourceKey key, byte[] array) {
        set(key.toResourceString(), array);
    }

    @Override
    public void set(ResourceKey key, int[] array) {
        set(key.toResourceString(), array);
    }

    @Override
    public void set(ResourceKey key, long[] array) {
        set(key.toResourceString(), array);
    }

    @Override
    public void set(ResourceKey key, IPlatformData data) {
        if (!(data instanceof SpigotMemoryData)) {
            return;
        }
        set(key.toResourceString(), ((SpigotMemoryData) data).getHandle());
    }

    @Override
    public byte getOrDefault(ResourceKey key, byte value) {
        Object object = get(key.toResourceString());
        if (object != null && Primitives.toPrimitive(object.getClass()) == byte.class) {
            if (Primitives.isPrimitive(object.getClass())) {
                return (byte) object;
            }
            return (Byte) object;
        }
        return value;
    }

    @Override
    public short getOrDefault(ResourceKey key, short value) {
        Object object = get(key.toResourceString());
        if (object != null && Primitives.toPrimitive(object.getClass()) == short.class) {
            if (Primitives.isPrimitive(object.getClass())) {
                return (short) object;
            }
            return (Short) object;
        }
        return value;
    }

    @Override
    public int getOrDefault(ResourceKey key, int value) {
        Object object = get(key.toResourceString());
        if (object != null && Primitives.toPrimitive(object.getClass()) == int.class) {
            if (Primitives.isPrimitive(object.getClass())) {
                return (int) object;
            }
            return (Integer) object;
        }
        return value;
    }

    @Override
    public long getOrDefault(ResourceKey key, long value) {
        Object object = get(key.toResourceString());
        if (object != null && Primitives.toPrimitive(object.getClass()) == long.class) {
            if (Primitives.isPrimitive(object.getClass())) {
                return (long) object;
            }
            return (long) object;
        }
        return value;
    }

    @Override
    public float getOrDefault(ResourceKey key, float value) {
        Object object = get(key.toResourceString());
        if (object != null && Primitives.toPrimitive(object.getClass()) == float.class) {
            if (Primitives.isPrimitive(object.getClass())) {
                return (float) object;
            }
            return (float) object;
        }
        return value;
    }

    @Override
    public double getOrDefault(ResourceKey key, double value) {
        Object object = get(key.toResourceString());
        if (object != null && Primitives.toPrimitive(object.getClass()) == double.class) {
            if (Primitives.isPrimitive(object.getClass())) {
                return (double) object;
            }
            return (double) object;
        }
        return value;
    }

    @Override
    public String getOrDefault(ResourceKey key, String value) {
        Object object = get(key.toResourceString());
        if (object != null && object instanceof String) {
            return (String) object;
        }
        return value;
    }

    @Override
    public byte[] getOrDefault(ResourceKey key, byte[] value) {
        Object object = get(key.toResourceString());
        if (object != null && object instanceof byte[]) {
            return (byte[]) object;
        }
        return value;
    }

    @Override
    public int[] getOrDefault(ResourceKey key, int[] value) {
        Object object = get(key.toResourceString());
        if (object != null && object instanceof int[]) {
            return (int[]) object;
        }
        return value;
    }

    @Override
    public long[] getOrDefault(ResourceKey key, long[] value) {
        Object object = get(key.toResourceString());
        if (object != null && object instanceof long[]) {
            return (long[]) object;
        }
        return value;
    }

    @Override
    public byte getByte(ResourceKey key) {
        return getOrDefault(key, (byte) 0);
    }

    @Override
    public short getShort(ResourceKey key) {
        return getOrDefault(key, (short) 0);
    }

    @Override
    public int getInt(ResourceKey key) {
        return getOrDefault(key, 0);
    }

    @Override
    public long getLong(ResourceKey key) {
        return getOrDefault(key, 0L);
    }

    @Override
    public float getFloat(ResourceKey key) {
        return getOrDefault(key, 0f);
    }

    @Override
    public double getDouble(ResourceKey key) {
        return getOrDefault(key, 0d);
    }

    @Override
    public String getString(ResourceKey key) {
        Object object = get(key.toResourceString());
        if (object == null || !(object instanceof String)) {
            return null;
        }
        return (String) object;
    }

    @Override
    public byte[] getByteArray(ResourceKey key) {
        Object object = get(key.toResourceString());
        if (object == null || !(object instanceof byte[])) {
            return null;
        }
        return (byte[]) object;
    }

    @Override
    public int[] getIntArray(ResourceKey key) {
        Object object = get(key.toResourceString());
        if (object == null || !(object instanceof int[])) {
            return null;
        }
        return (int[]) object;
    }

    @Override
    public long[] getLongArray(ResourceKey key) {
        Object object = get(key.toResourceString());
        if (object == null || !(object instanceof long[])) {
            return null;
        }
        return (long[]) object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IPlatformData getData(ResourceKey key) {
        Object object = get(key.toResourceString());
        if (object == null || !(object instanceof ConcurrentHashMap)) {
            return null;
        }
        return new SpigotMemoryData((ConcurrentHashMap<String, Object>) object);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IPlatformData getDataOrNew(ResourceKey key) {
        String stringKey = key.toResourceString();
        Object object = get(stringKey);
        if (object == null || !(object instanceof ConcurrentHashMap)) {
            SpigotMemoryData data = new SpigotMemoryData();
            set(stringKey, data.getHandle());
            return data;
        }
        return new SpigotMemoryData((ConcurrentHashMap<String, Object>) object);
    }

    @Override
    public boolean hasByte(ResourceKey key) {
        Object object = get(key.toResourceString());
        return object != null && (object.getClass() == Byte.class || object.getClass() == byte.class);
    }

    @Override
    public boolean hasShort(ResourceKey key) {
        Object object = get(key.toResourceString());
        return object != null && (object.getClass() == Short.class || object.getClass() == short.class);
    }

    @Override
    public boolean hasInt(ResourceKey key) {
        Object object = get(key.toResourceString());
        return object != null && (object.getClass() == Integer.class || object.getClass() == int.class);
    }

    @Override
    public boolean hasLong(ResourceKey key) {
        Object object = get(key.toResourceString());
        return object != null && (object.getClass() == Long.class || object.getClass() == long.class);
    }

    @Override
    public boolean hasFloat(ResourceKey key) {
        Object object = get(key.toResourceString());
        return object != null && (object.getClass() == Float.class || object.getClass() == float.class);
    }

    @Override
    public boolean hasDouble(ResourceKey key) {
        Object object = get(key.toResourceString());
        return object != null && (object.getClass() == Double.class || object.getClass() == double.class);
    }

    @Override
    public boolean hasString(ResourceKey key) {
        Object object = get(key.toResourceString());
        return object != null && object.getClass() == String.class;
    }

    @Override
    public boolean hasByteArray(ResourceKey key) {
        Object object = get(key.toResourceString());
        return object != null && object.getClass() == byte[].class;
    }

    @Override
    public boolean hasIntArray(ResourceKey key) {
        Object object = get(key.toResourceString());
        return object != null && object.getClass() == int[].class;
    }

    @Override
    public boolean hasLongArray(ResourceKey key) {
        Object object = get(key.toResourceString());
        return object != null && object.getClass() == long[].class;
    }

    @Override
    public boolean hasData(ResourceKey key) {
        Object object = get(key.toResourceString());
        return object != null && object instanceof ConcurrentHashMap;
    }

}
