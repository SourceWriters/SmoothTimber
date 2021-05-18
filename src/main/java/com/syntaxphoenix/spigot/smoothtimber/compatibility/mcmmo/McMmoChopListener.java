package com.syntaxphoenix.spigot.smoothtimber.compatibility.mcmmo;

import com.gmail.nossr50.config.experience.ExperienceConfig;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.util.player.UserManager;
import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;

public class McMmoChopListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChopEvent(AsyncPlayerChopTreeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        McMMOPlayer player = UserManager.getPlayer(event.getPlayer());
        if (player == null || !PrimarySkillType.WOODCUTTING.getPermissions(event.getPlayer())) {
            return;
        }

        SmoothTimber.get().getServer().getScheduler().runTask(SmoothTimber.get(), () -> {
            for (Location location : event.getBlockLocations()) {
                if (!hasWoodcuttingXP(location.getBlock())) {
                    continue;
                }
                if (mcMMO.getPlaceStore().isTrue(location.getBlock().getState())) {
                    continue;
                }
                player.getWoodcuttingManager().processWoodcuttingBlockXP(location.getBlock().getState());
                player.getWoodcuttingManager().processHarvestLumber(location.getBlock().getState());
            }
        });
    }

    private boolean hasWoodcuttingXP(Block block) {
        return ExperienceConfig.getInstance().doesBlockGiveSkillXP(PrimarySkillType.WOODCUTTING, block.getBlockData());
    }

}
