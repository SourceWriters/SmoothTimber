package com.syntaxphoenix.spigot.smoothtimber.tasks;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown.CooldownHelper;
import com.syntaxphoenix.spigot.smoothtimber.utilities.limit.Limiter;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;
import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

public class BlockBreakEventTask extends BukkitRunnable {
		private final Player player;
		private final Location location;
		private final VersionChanger change;
		private final ItemStack tool;
		private final ArrayList<Location> woodBlocks;
		private int limit = 0; 
		private final int maxItems;
		private RandomNumberGenerator generator;

		public BlockBreakEventTask(Player player, Location location, VersionChanger change, ItemStack tool, ArrayList<Location> woodBlocks, int maxItems, RandomNumberGenerator generator) {
			this.player = player;
			this.location = location;
			this.change = change;
			this.tool = tool;
			this.woodBlocks = woodBlocks;
			this.limit = Limiter.getLimit(player);
			this.maxItems = maxItems;
			this.generator = generator;
		}	

		@Override
		public void run() 
		{
			if (SmoothTimber.triggerChopEvent(player, location, change, tool, woodBlocks, limit)) {
				CooldownHelper.reset(player);
				return;
			}
			SmoothTimber.triggerChoppedEvent(player, location, change, tool, woodBlocks, limit);
			BlockBreakTask blockBreakTask = new BlockBreakTask(player, location, change, tool, woodBlocks, maxItems, generator);
			if (SmoothTimber.IS_FOLIA) {
                Bukkit.getServer().getRegionScheduler().run(PluginUtils.MAIN, location, value -> blockBreakTask.run());
            } else {
                Bukkit.getScheduler().runTask(PluginUtils.MAIN, blockBreakTask);
            }
		}
}
