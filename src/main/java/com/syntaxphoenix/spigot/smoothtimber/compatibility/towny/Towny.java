package com.syntaxphoenix.spigot.smoothtimber.compatibility.towny;

import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class Towny extends CompatibilityAddon {

    private TownyChopListener listener;

    @Override
    public void onEnable(PluginPackage pluginPackage, SmoothTimber smoothTimber) throws Exception {
        Bukkit.getServer().getPluginManager().registerEvents(listener = new TownyChopListener(this), smoothTimber);
    }

    @Override
    public void onDisable(SmoothTimber smoothTimber) throws Exception {
        if (listener != null) {
            HandlerList.unregisterAll(listener);
        }
    }

    public boolean canDestroy(Player player, Block block) {
        return PlayerCacheUtil.getCachePermission(player, block.getLocation(), block.getType(),
                TownyPermission.ActionType.DESTROY);
    }

}
