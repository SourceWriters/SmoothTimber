package com.syntaxphoenix.spigot.smoothtimber.command;

import static com.syntaxphoenix.spigot.smoothtimber.SmoothTimber.COMMANDS;
import static com.syntaxphoenix.syntaxapi.command.ArgumentSerializer.DEFAULT;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.syntaxapi.command.Arguments;
import com.syntaxphoenix.syntaxapi.command.BaseCommand;
import com.syntaxphoenix.syntaxapi.command.CommandProcess;
import com.syntaxphoenix.syntaxapi.command.ExecutionState;

public class CommandRedirect implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command ignore, String alias, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + Message.COMMAND_NON222EXISTENT.colored());
            return true;
        }

        if (!sender.hasPermission("smoothtimber.use")) {
            sender.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + Message.GLOBAL_NOT222ALLOWED.colored(new String[] {
                "%permission%",
                "smoothtimber.use"
            }));
            return true;
        }

        CommandProcess process = prepare(sender, args);

        if (!process.isValid() || process.getCommand() == null) {
            sender.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + Message.COMMAND_NON222EXISTENT.colored());
            return true;
        }

        ExecutionState state = process.execute(COMMANDS);

        return state == ExecutionState.SUCCESS;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command ignore, String alias, String[] args) {

        ArrayList<String> output = new ArrayList<>();

        if (args.length == 0) {
            return output;
        }

        CommandProcess process = prepare(sender, args);

        if (!process.isValid() || process.getCommand() == null) {
            return output;
        }

        BaseCommand command = process.getCommand();
        if (!(command instanceof SmoothCommand)) {
            return output;
        }

        MinecraftInfo info = (MinecraftInfo) process.constructInfo();
        Arguments arguments = process.getArguments();

        Arguments completion = ((SmoothCommand) command).complete(info, arguments).getCompletion();

        int size = completion.count();
        for (int index = 1; index <= size; index++) {
            output.add(DEFAULT.toString(completion.get(index)));
        }

        return output;
    }

    private CommandProcess prepare(CommandSender sender, String[] arguments) {
        CommandProcess process = COMMANDS.process(arguments);
        process.setInfoConstructor((manager, label) -> new MinecraftInfo(manager, label, sender));
        return process;
    }

}
