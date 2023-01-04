package com.syntaxphoenix.spigot.smoothtimber.command.commands;

import org.bukkit.command.CommandSender;

import com.syntaxphoenix.spigot.smoothtimber.command.MinecraftInfo;
import com.syntaxphoenix.spigot.smoothtimber.command.SmoothCommand;
import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.syntaxapi.command.Arguments;
import com.syntaxphoenix.syntaxapi.command.DefaultCompletion;

public class HelpCommand extends SmoothCommand {

    @Override
    public void execute(final MinecraftInfo info, final Arguments arguments) {

        final CommandSender sender = info.getSender();

        sender.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + Message.COMMAND_USAGE_HELP.colored());
        sender.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + Message.COMMAND_USAGE_TOGGLE.colored());

    }

    @Override
    public DefaultCompletion complete(final MinecraftInfo info, final Arguments arguments) {
        return new DefaultCompletion();
    }

}