package com.syntaxphoenix.spigot.smoothtimber.compatibility.uxmclaims;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChopTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.event.reason.DefaultReason;
import com.uxplima.claim.app.facade.ClaimFacade;
import com.uxplima.claim.bukkit.api.BukkitConverter;
import com.uxplima.claim.bukkit.api.UxmClaimBukkitAPI;
import com.uxplima.claim.domain.model.Claim;
import com.uxplima.claim.domain.model.enums.ClaimPermission;
import com.uxplima.claim.domain.model.vo.Location;

public class UxmClaimsChopListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onChopEvent(AsyncPlayerChopTreeEvent event) {
        ClaimFacade claimFacade = UxmClaimBukkitAPI.getInstance().claimFacade();
        UUID uuid = event.getPlayer().getUniqueId();

        for (org.bukkit.Location location : event.getBlockLocations()) {
            // Convert Bukkit location to valid domain location
            Location domainLoc = BukkitConverter.toDomainLocation(location);

            // Find claim at check location
            Claim claim = claimFacade.getByLocationUnsafe(domainLoc);
            if (claim == null || claim.hasPermission(uuid, ClaimPermission.BLOCK_BREAK)) {
                continue;
            }

            // Deny if neither owner nor member with permission
            event.setCancelled(true);
            event.setReason(DefaultReason.UXMCLAIMS);
            return;
        }
    }
}
