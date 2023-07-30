package com.syntaxphoenix.spigot.smoothtimber.compatibility.factionsuuid;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.config.file.MainConfig;
import com.massivecraft.factions.listeners.FactionsBlockListener;
import com.massivecraft.factions.perms.PermissibleActions;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.reason.DefaultReason;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionsUUIDChopListener implements Listener {
    
    private MainConfig config;
    private FPlayers players;
    
    public FactionsUUIDChopListener() {
        this.config = FactionsPlugin.getInstance().conf();
        this.players = FPlayers.getInstance();
    }

    @EventHandler
    public void onAsyncPlayerChopTree(final AsyncPlayerChopTreeEvent event) {
        Player player = event.getPlayer();
        if (config.factions().protection().getPlayersWhoBypassAllProtection().contains(player.getName()) || players.getByPlayer(player).isAdminBypassing()) {
            return;
        }
        for(Location bktLocation : event.getBlockLocations()) {
            if (!FactionsBlockListener.playerCanBuildDestroyBlock(event.getPlayer(), bktLocation, PermissibleActions.DESTROY, true)) {
                event.setCancelled(true);
                event.setReason(DefaultReason.FACTIONS);
                return;
            }
        }
    }

}
