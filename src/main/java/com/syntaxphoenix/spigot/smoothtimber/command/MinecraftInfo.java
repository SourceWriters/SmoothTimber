package com.syntaxphoenix.spigot.smoothtimber.command;

import org.bukkit.command.CommandSender;

import com.syntaxphoenix.syntaxapi.command.BaseInfo;
import com.syntaxphoenix.syntaxapi.command.CommandManager;

public class MinecraftInfo extends BaseInfo {

    private final CommandSender sender;

    public MinecraftInfo(final CommandManager manager, final String label, final CommandSender sender) {
        super(manager, label);
        this.sender = sender;
    }

    public CommandSender getSender() {
        return sender;
    }

}
