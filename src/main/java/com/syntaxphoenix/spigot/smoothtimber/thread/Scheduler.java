package com.syntaxphoenix.spigot.smoothtimber.thread;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;

public class Scheduler {
  private static RegionScheduler regionScheduler = Bukkit.getServer().getRegionScheduler();
  private static GlobalRegionScheduler gRegionScheduler = Bukkit.getServer().getGlobalRegionScheduler();
  private static AsyncScheduler asyncScheduler = Bukkit.getServer().getAsyncScheduler();

  private Scheduler()
  {
    throw new IllegalStateException("Scheduler class");
  }

  public static void cancelTasks(Plugin plugin)
  {
    asyncScheduler.cancelTasks(plugin);
    gRegionScheduler.cancelTasks(plugin);
  }

  public static void runRegion(Plugin plugin, Location loc, Runnable task)
  {
    regionScheduler.run(plugin, loc, value -> task.run());
  }

  public static void runAsync(Plugin plugin, Runnable task)
  {
    asyncScheduler.runNow(plugin, value -> task.run());
  }

  public static Object runAsyncFixedRate(Plugin plugin, Runnable task, long delay, long period)
  {
    return asyncScheduler.runAtFixedRate(plugin, value -> task.run(), delay, period, TimeUnit.MILLISECONDS);
  }
}
