package net.sourcewriters.smoothtimber.spigot.version;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.smoothtimber.api.platform.world.data.IPlatformData;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntity;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntityData;
import net.sourcewriters.smoothtimber.spigot.world.data.SpigotMetaData;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotEntity;

final class EntityData1_13<E extends IPlatformEntity> implements IPlatformEntityData<E> {

    private final E entity;

    private final SpigotMetaData metaData;

    @SuppressWarnings("unchecked")
    public EntityData1_13(final Plugin plugin, final E entity) {
        this.entity = entity;
        this.metaData = new SpigotMetaData(plugin, ((SpigotEntity<E, ?>) entity).getHandle());
    }

    @Override
    public E getEntity() {
        return entity;
    }

    @Override
    public boolean isPersistentDataSupported() {
        return false;
    }

    @Override
    public IPlatformData getPersistentData() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Persistent data is not supported in this version");
    }

    @Override
    public IPlatformData getMetaData() {
        return metaData;
    }

}
