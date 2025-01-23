package com.syntaxphoenix.spigot.smoothtimber.version;

import java.lang.reflect.Field;

import org.bukkit.enchantments.Enchantment;

public final class VersionConstant {

    public static final Enchantment ENCHANTMENT_UNBREAKING = get(Enchantment.class, "UNBREAKING", "DURABILITY");
    public static final Enchantment ENCHANTMENT_LOOTING = get(Enchantment.class, "LOOTING", "LOOT_BONUS_BLOCKS");

    private static <E> E get(Class<E> type, String... fieldNames) {
        Field field;
        for (String fieldName : fieldNames) {
            try {
                field = type.getDeclaredField(fieldName);
            } catch (NoSuchFieldException | SecurityException e) {
                continue;
            }
            try {
                return type.cast(field.get(null));
            } catch (IllegalArgumentException | IllegalAccessException e) {
            }
        }
        return null;
    }

    private VersionConstant() {
        throw new UnsupportedOperationException();
    }

}
