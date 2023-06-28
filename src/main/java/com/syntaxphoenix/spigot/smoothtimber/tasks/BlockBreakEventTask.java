package com.syntaxphoenix.spigot.smoothtimber.tasks;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown.CooldownHelper;
import com.syntaxphoenix.spigot.smoothtimber.utilities.limit.Limiter;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;

public class BlockBreakEventTask extends BukkitRunnable {
		private final Player player;
		private final Location location;
		private final VersionChanger change;
		private final ItemStack tool;
		private final ArrayList<Location> woodBlocks;
		private int limit = 0; 

		public BlockBreakEventTask(Player player, Location location, VersionChanger change, ItemStack tool, ArrayList<Location> woodBlocks, int maxItems) {
			this.player = player;
			this.location = location;
			this.change = change;
			this.tool = tool;
			this.woodBlocks = woodBlocks;
			this.limit = Limiter.getLimit(player);
		}	

		@Override
		public void run() 
		{
			if (SmoothTimber.triggerChopEvent(player, location, change, tool, woodBlocks, limit)) {
				CooldownHelper.reset(player);
				return;
			}
			SmoothTimber.triggerChoppedEvent(player, location, change, tool, woodBlocks, limit);
		}
}
