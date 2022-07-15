package net.sourcewriters.smoothtimber.spigot.world.data;

import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.function.BiFunction;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public final class SpigotDataType<T, Z> implements PersistentDataType<T, Z> {

    public static final PersistentDataType<byte[], UUID> UUID = new SpigotDataType<>(byte[].class, UUID.class, (complex, context) -> {
        ByteBuffer buf = ByteBuffer.allocate(16);
        buf.putLong(complex.getMostSignificantBits());
        buf.putLong(complex.getLeastSignificantBits());
        return buf.array();
    }, (primitive, context) -> {
        ByteBuffer buf = ByteBuffer.wrap(primitive);
        long most = buf.getLong();
        long least = buf.getLong();
        return new UUID(most, least);
    });

    private final BiFunction<Z, PersistentDataAdapterContext, T> toPrimitive;
    private final BiFunction<T, PersistentDataAdapterContext, Z> fromPrimitive;

    private final Class<T> primitiveType;
    private final Class<Z> complexType;

    public SpigotDataType(Class<T> primitiveType, Class<Z> complexType, BiFunction<Z, PersistentDataAdapterContext, T> toPrimitive,
        BiFunction<T, PersistentDataAdapterContext, Z> fromPrimitive) {
        this.primitiveType = primitiveType;
        this.complexType = complexType;
        this.toPrimitive = toPrimitive;
        this.fromPrimitive = fromPrimitive;
    }

    @Override
    public Class<T> getPrimitiveType() {
        return primitiveType;
    }

    @Override
    public Class<Z> getComplexType() {
        return complexType;
    }

    @Override
    public T toPrimitive(Z complex, PersistentDataAdapterContext context) {
        return toPrimitive.apply(complex, context);
    }

    @Override
    public Z fromPrimitive(T primitive, PersistentDataAdapterContext context) {
        return fromPrimitive.apply(primitive, context);
    }

}
