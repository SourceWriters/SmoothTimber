package com.syntaxphoenix.spigot.smoothtimber.compatibility.factionsuuid;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.perms.PermissibleActions;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.reason.DefaultReason;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionsUUIDChopListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChopTree(final AsyncPlayerChopTreeEvent event) {
        FPlayer player = FPlayers.getInstance().getByPlayer(event.getPlayer());
        if (event.getBlockLocations().stream()
                .map(FLocation::new)
                .distinct()
                .anyMatch(location -> !Board.getInstance().getFactionAt(location).hasAccess(player, PermissibleActions.DESTROY, location))
        ) {
            event.setCancelled(true);
            event.setReason(DefaultReason.FACTIONS);
        }
    }

}
