package com.syntaxphoenix.spigot.smoothtimber.utilities;

import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;

import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public class Storage {

    public static final AbstractReflect FALLING_BLOCK = new Reflect(FallingBlock.class).searchMethod("id", "getBlockId")
        .searchMethod("data", "getBlockData");

    public static final AbstractReflect MATERIAL = new Reflect(Material.class).searchMethod("type", "getMaterial", int.class);

}
