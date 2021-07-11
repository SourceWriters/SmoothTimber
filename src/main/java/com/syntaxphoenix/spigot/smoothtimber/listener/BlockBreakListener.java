package com.syntaxphoenix.spigot.smoothtimber.listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerTreeFallEvent;
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
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        VersionChanger change = PluginUtils.CHANGER;
        if (!change.isWoodBlock(event.getBlock())) {
            return;
        }

        Player player = event.getPlayer();

        if (!PlayerState.isPermitted(player)) {
            return;
        }

        if (CutterConfig.ENABLE_WORLD) {
            boolean contains = CutterConfig.WORLD_LIST.contains(event.getBlock().getWorld().getName());
            if (CutterConfig.ENABLE_WORLD_BLACKLIST ? contains : !contains) {
                return;
            }
        }

        if (CutterConfig.SNEAK.test(() -> player.isSneaking())) {
            return;
        }

        if (CutterConfig.TOGGLEABLE.test(() -> SmoothTimber.STORAGE.hasToggled(player.getUniqueId()))) {
            return;
        }

        if (CutterConfig.ENABLE_COOLDOWN && CooldownHelper.isTriggered(player.getUniqueId())) {
            String time = CooldownHelper.getFormattedTime(player.getUniqueId());
            player.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + Message.COOLDOWN_WAIT.colored(new String[] {
                "%time%",
                time
            }));
            event.setCancelled(true);
            return;
        }

        if (change.hasCuttingItemInHand(player)) {
            ItemStack tool = change.getItemInHand(player);
            if (!change.hasPermissionForCuttingItem(player, tool)) {
                return;
            }
            Location location = event.getBlock().getLocation();
            if (Locator.isPlayerPlaced(location)) {
                return;
            }
            event.setCancelled(true);
            CooldownHelper.trigger(player);
            Bukkit.getScheduler().runTaskAsynchronously(PluginUtils.MAIN, new Runnable() {
                @Override
                public void run() {
                    int maxItems = CutterConfig.ENABLE_LUCK ? change.getMaxDropCount(tool) : 1;
                    ArrayList<Location> woodBlocks = new ArrayList<>();
                    int limit = Limiter.getLimit(player);
                    Locator.locateWood(location, woodBlocks, limit);
                    if (SmoothTimber.triggerChopEvent(player, location, change, tool, woodBlocks, limit)) {
                        CooldownHelper.reset(player);
                        return;
                    }
                    SmoothTimber.triggerChoppedEvent(player, location, change, tool, woodBlocks, limit);
                    Bukkit.getScheduler().runTask(PluginUtils.MAIN, new Runnable() {
                        @Override
                        public void run() {
                            AsyncPlayerTreeFallEvent event = SmoothTimber.buildFallEvent(player, location, change, tool);
                            for (Location woodBlock : woodBlocks) {
                                Block block = woodBlock.getBlock();
                                WoodType wood = change.getWoodTypeFromBlock(block);
                                if ((wood != null && block != null) && change.hasPermissionForWoodType(player, wood)) {
                                    if (player.getGameMode() != GameMode.CREATIVE && change.removeDurabilityFromItem(tool) == null) {
                                        break;
                                    }
                                    event.add(wood);
                                    change.toFallingBlock(block).setMetadata("STAnimate",
                                        new FixedMetadataValue(SmoothTimber.get(), maxItems <= 1 ? maxItems : generateAmount(maxItems)));
                                }
                            }
                            Bukkit.getScheduler().runTaskAsynchronously(PluginUtils.MAIN, () -> SmoothTimber.triggerFallEvent(event));
                        }

                        private int generateAmount(int max) {
                            int drop = 1;
                            float more = 1f / (max + 1);
                            float previous = more * 2f;
                            float next = more * 3f;
                            float chance = generator.nextFloat() * (float) CutterConfig.LUCK_MULTIPLIER;
                            while (true) {
                                if (previous < chance && chance > next) {
                                    drop++;
                                    previous = next;
                                    next += more;
                                } else if (previous < chance && chance < next) {
                                    drop++;
                                    break;
                                } else {
                                    break;
                                }
                            }
                            return Math.min(drop, 64);
                        }

                    });
                }
            });
        }

    }
}
