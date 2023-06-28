package com.syntaxphoenix.spigot.smoothtimber.listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.tasks.BlockBreakEventTask;
import com.syntaxphoenix.spigot.smoothtimber.tasks.BlockBreakTask;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PlayerState;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown.CooldownHelper;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;
import com.syntaxphoenix.syntaxapi.random.NumberGeneratorType;
import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

public class BlockBreakListener implements Listener {

    private final RandomNumberGenerator generator = NumberGeneratorType.MURMUR.create(System.currentTimeMillis() >> 3);

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockBreak(final BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final VersionChanger change = PluginUtils.CHANGER;
        if (!change.isWoodBlock(event.getBlock())) {
            return;
        }

        final Player player = event.getPlayer();

        if (!PlayerState.isPermitted(player)) {
            return;
        }

        if (CutterConfig.ENABLE_WORLD) {
            final boolean contains = CutterConfig.WORLD_LIST.contains(event.getBlock().getWorld().getName());
            if (CutterConfig.ENABLE_WORLD_BLACKLIST == contains) {
                return;
            }
        }

        if (CutterConfig.SNEAK.test(() -> player.isSneaking()) || CutterConfig.TOGGLEABLE.test(() -> SmoothTimber.STORAGE.hasToggled(player.getUniqueId()))) {
            return;
        }

        if (CutterConfig.ENABLE_COOLDOWN && CooldownHelper.isTriggered(player.getUniqueId())) {
            final String time = CooldownHelper.getFormattedTime(player.getUniqueId());
            player.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + Message.COOLDOWN_WAIT.colored(new String[] {
                "%time%",
                time
            }));
            event.setCancelled(true);
            return;
        }

        if (change.hasCuttingItemInHand(player)) {
            final ItemStack tool = change.getItemInHand(player);
            if (!change.hasPermissionForCuttingItem(player, tool)) {
                return;
            }
            final Location location = event.getBlock().getLocation();
            if (Locator.isPlayerPlaced(location)) {
                return;
            }
            event.setCancelled(true);
            CooldownHelper.trigger(player);
            final int maxItems = CutterConfig.ENABLE_LUCK ? change.getMaxDropCount(tool) : 1;
            final ArrayList<Location> woodBlocks = new ArrayList<>();

            BlockBreakEventTask blockBreakEventTask = new BlockBreakEventTask(player, location, change, tool, woodBlocks, maxItems, generator);
            if (SmoothTimber.IS_FOLIA) {
                Bukkit.getServer().getAsyncScheduler().runNow(PluginUtils.MAIN, value -> blockBreakEventTask.run());
            } else {
                Bukkit.getScheduler().runTaskAsynchronously(PluginUtils.MAIN, blockBreakEventTask);
            }
        }

    }
}
