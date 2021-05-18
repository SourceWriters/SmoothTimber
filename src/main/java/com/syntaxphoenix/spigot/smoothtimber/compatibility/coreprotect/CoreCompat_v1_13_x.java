package com.syntaxphoenix.spigot.smoothtimber.compatibility.coreprotect;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import net.coreprotect.CoreProtectAPI;

public class CoreCompat_v1_13_x implements CoreCompat {

    private final CoreProtectAPI api;

    protected CoreCompat_v1_13_x(Plugin plugin) {
        api = ((net.coreprotect.CoreProtect) plugin).getAPI();
    }

    @Override
    public void logRemoval(String user, Location location, Block block) {
        api.logRemoval(user, location, block.getType(), block.getBlockData());
    }

}
