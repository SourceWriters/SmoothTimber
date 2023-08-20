package com.syntaxphoenix.spigot.smoothtimber.listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerTreeFallEvent;
import com.syntaxphoenix.spigot.smoothtimber.platform.Platform;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PlayerState;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown.CooldownHelper;
import com.syntaxphoenix.spigot.smoothtimber.utilities.limit.Limiter;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.WoodType;
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
            Platform.getPlatform().asyncTask((Runnable) () -> {
                final int maxItems = CutterConfig.ENABLE_LUCK ? change.getMaxDropCount(tool) : 1;
                final ArrayList<Location> woodBlocks = new ArrayList<>();
                final int limit = Limiter.getLimit(player);
                Locator.locateWood(location, woodBlocks, limit);
                if (SmoothTimber.triggerChopEvent(player, location, change, tool, woodBlocks, limit)) {
                    CooldownHelper.reset(player);
                    return;
                }
                SmoothTimber.triggerChoppedEvent(player, location, change, tool, woodBlocks, limit);
                Platform.getPlatform().regionalSyncTask(location, new Runnable() {
                    @Override
                    public void run() {
                        final AsyncPlayerTreeFallEvent event = SmoothTimber.buildFallEvent(player, location, change, tool);
                        final boolean animated = CutterConfig.ENABLE_ANIMATION;
                        final boolean collect = CutterConfig.INSTANT_COLLECT;
                        final Plugin plugin = SmoothTimber.get();
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
                        Platform.getPlatform().asyncTask(() -> SmoothTimber.triggerFallEvent(event));
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

                });
            });
        }

    }
}
