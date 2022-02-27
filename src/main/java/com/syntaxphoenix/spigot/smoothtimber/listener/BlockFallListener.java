package com.syntaxphoenix.spigot.smoothtimber.listener;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

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
        extractItem(change, block);
        block.remove();
    }

    private void extractItem(VersionChanger change, FallingBlock block) {
        List<MetadataValue> values = block.getMetadata("STCollect");
        int amount = block.getMetadata("STAnimate").get(0).asInt();
        UUID target;
        if (values.isEmpty() || (target = parse(values.get(0).asString())) == null) {
            change.dropItemByFallingBlock(block, amount);
            return;
        }
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            change.dropItemByFallingBlock(block, amount);
            return;
        }
        ItemStack item = change.getItemFromFallingBlock(block);
        item.setAmount(amount);
        player.getInventory().addItem(item);
    }

    private UUID parse(String value) {
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException exp) {
            return null;
        }
    }

}
