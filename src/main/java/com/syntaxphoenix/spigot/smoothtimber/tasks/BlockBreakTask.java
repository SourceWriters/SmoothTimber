package com.syntaxphoenix.spigot.smoothtimber.tasks;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerTreeFallEvent;
import com.syntaxphoenix.spigot.smoothtimber.thread.Scheduler;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.limit.Limiter;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.WoodType;
import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

public class BlockBreakTask extends BukkitRunnable {

    private final Player player;
    private final Location location;
    private final VersionChanger change;
    private final ItemStack tool;
    private final ArrayList<Location> woodBlocks;
    private final int maxItems;
    private RandomNumberGenerator generator;

    public BlockBreakTask(Player player, Location location, VersionChanger change, ItemStack tool, ArrayList<Location> woodBlocks, int maxItems, RandomNumberGenerator generator) {
        this.player = player;
        this.location = location;
        this.change = change;
        this.tool = tool;
        this.woodBlocks = woodBlocks;
        this.maxItems = maxItems;
        this.generator = generator;
    }

    @Override
    public void run() {
        final AsyncPlayerTreeFallEvent event = SmoothTimber.buildFallEvent(player, location, change, tool);
        final boolean animated = CutterConfig.ENABLE_ANIMATION;
        final boolean collect = CutterConfig.INSTANT_COLLECT;
        final Plugin plugin = SmoothTimber.get();

        Locator.locateWood(location, woodBlocks, Limiter.getLimit(player));

        for (final Location woodBlock : woodBlocks) {
            final Block block = woodBlock.getBlock();
            final WoodType wood = change.getWoodTypeFromBlock(block);
            if (wood != null && block != null && change.hasPermissionForWoodType(player, wood)) {
                if (player.getGameMode() != GameMode.CREATIVE && change.removeDurabilityFromItem(tool) == null) {
                    break;
                }

                event.add(wood);
                final int amount = maxItems <= 1 ? maxItems : generateAmount(maxItems);
                if (animated) {
                    final Entity entity = change.toFallingBlock(block);
                    entity.setMetadata("STAnimate", new FixedMetadataValue(plugin, amount));
                    if (collect) {
                        entity.setMetadata("STCollect",
                                new FixedMetadataValue(plugin, player.getUniqueId().toString()));
                    }
                    continue;
                }
                if (collect) {
                    final ItemStack stack = change.getItemFromBlock(block);
                    stack.setAmount(amount);
                    player.getInventory().addItem(stack);
                    continue;
                }
                change.dropItemByBlock(block, amount);
            }
        }
        if (SmoothTimber.IS_FOLIA) {
            Scheduler.runAsync(PluginUtils.MAIN, new Runnable () {
                @Override
                public void run() {
                    SmoothTimber.triggerFallEvent(event);
                }
            });
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(PluginUtils.MAIN, () -> SmoothTimber.triggerFallEvent(event));
        }
    }

    private int generateAmount(final int max) {
        int drop = 1;
        final float more = 1f / (max + 1);
        float previous = more * 2f;
        float next = more * 3f;
        final float chance = generator.nextFloat() * (float) CutterConfig.LUCK_MULTIPLIER;
        while (true) {
            if (previous < chance && chance > next) {
                drop++;
                previous = next;
                next += more;
            } else {
                if (previous < chance && chance < next) {
                    drop++;
                }
                break;
            }
        }
        return Math.min(drop, 64);
    }

}
