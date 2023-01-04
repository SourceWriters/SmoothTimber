package com.syntaxphoenix.spigot.smoothtimber.utilities;

import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.meta.ItemMeta;

import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public final class Storage {
    
    private Storage() {
        throw new UnsupportedOperationException();
    }
    
    private static final Reflect SELF = new Reflect(Storage.class);

    public static final AbstractReflect FALLING_BLOCK = new Reflect(FallingBlock.class).searchMethod("id", "getBlockId")
        .searchMethod("data", "getBlockData");

    public static final AbstractReflect MATERIAL = new Reflect(Material.class).searchMethod("type", "getMaterial", int.class);
    
    public static final AbstractReflect META_SPIGOT = new Reflect(ItemMeta.class).searchMethod("spigot", "spigot");
    public static final AbstractReflect UNBREAKABLE = createOptional(META_SPIGOT.getMethod("spigot").getDeclaringClass()).searchMethod("unbreakable", "isUnbreakable");
    
    private static Reflect createOptional(Class<?> clazz) {
        if(clazz == null) {
            return SELF;
        }
        return new Reflect(clazz);
    }
}
