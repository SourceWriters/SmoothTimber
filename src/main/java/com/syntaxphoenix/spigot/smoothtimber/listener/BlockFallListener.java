package com.syntaxphoenix.spigot.smoothtimber.listener;

import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;

public class BlockFallListener implements Listener {

    @EventHandler
    public void onChange(EntityChangeBlockEvent event) {
        VersionChanger change = PluginUtils.CHANGER;
        if (event.getEntityType() != change.getFallingBlockType()) {
            return;
        }
        FallingBlock block = (FallingBlock) event.getEntity();
        if (!block.hasMetadata("STAnimate")) {
            return;
        }
        event.setCancelled(true);

        change.dropItemByFallingBlock(block, block.getMetadata("STAnimate").get(0).asInt());
        block.remove();
    }

}
