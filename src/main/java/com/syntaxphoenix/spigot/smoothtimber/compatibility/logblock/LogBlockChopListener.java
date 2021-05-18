package com.syntaxphoenix.spigot.smoothtimber.compatibility.logblock;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerChoppedTreeEvent;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;

import de.diddiz.LogBlock.Actor;
import de.diddiz.LogBlock.Consumer;

public class LogBlockChopListener implements Listener {

    private final Consumer logblockConsumer;

    public LogBlockChopListener(Consumer logblockConsumer) {
        this.logblockConsumer = logblockConsumer;
    }

    @EventHandler
    public void onChopEvent(AsyncPlayerChoppedTreeEvent event) {
        final Actor actor = new Actor("#sm_" + event.getPlayer().getName());
        for (Location location : event.getBlockLocations()) {
            logblockConsumer.queueBlockBreak(actor, location, Locator.getBlock(location).getBlockData());
        }
    }

}
