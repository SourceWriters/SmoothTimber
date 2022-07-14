package net.sourcewriters.smoothtimber.spigot.version;


import org.bukkit.plugin.Plugin;

import net.sourcewriters.smoothtimber.api.platform.world.data.IPlatformData;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntity;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntityData;
import net.sourcewriters.smoothtimber.spigot.world.data.SpigotMetaData;
import net.sourcewriters.smoothtimber.spigot.world.data.SpigotPersistentData;
import net.sourcewriters.smoothtimber.spigot.world.entity.SpigotEntity;

final class EntityData1_14<E extends IPlatformEntity> implements IPlatformEntityData<E> {

    private final E entity;

    private final SpigotPersistentData persistentData;
    private final SpigotMetaData metaData;
    
    @SuppressWarnings("unchecked")
    public EntityData1_14(final Plugin plugin, final E entity) {
        this.entity = entity;
        SpigotEntity<E, ?> spigot = ((SpigotEntity<E, ?>) entity);
        this.metaData = new SpigotMetaData(plugin, spigot.getHandle());
        this.persistentData = new SpigotPersistentData(spigot.getHandle().getPersistentDataContainer());
    }

    @Override
    public E getEntity() {
        return entity;
    }

    @Override
    public boolean isPersistentDataSupported() {
        return true;
    }

    @Override
    public IPlatformData getPersistentData() throws UnsupportedOperationException {
        return persistentData;
    }

    @Override
    public IPlatformData getMetaData() {
        return metaData;
    }

}
