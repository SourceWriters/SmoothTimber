package com.syntaxphoenix.spigot.smoothtimber.command.commands;

import static com.syntaxphoenix.spigot.smoothtimber.SmoothTimber.STORAGE;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.syntaxphoenix.spigot.smoothtimber.command.MinecraftInfo;
import com.syntaxphoenix.spigot.smoothtimber.command.SmoothCommand;
import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.syntaxapi.command.ArgumentType;
import com.syntaxphoenix.syntaxapi.command.Arguments;
import com.syntaxphoenix.syntaxapi.command.DefaultCompletion;
import com.syntaxphoenix.syntaxapi.command.NumericArgument;
import com.syntaxphoenix.syntaxapi.command.arguments.IntegerArgument;

public class ToggleCommand extends SmoothCommand {

    @Override
    public void execute(MinecraftInfo info, Arguments arguments) {

        CommandSender sender = info.getSender();
        if (!(sender instanceof Player)) {
            sender.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + Message.COMMAND_ONLY_PLAYER.colored());
            return;
        }

        if (!(sender.hasPermission("smoothtimber.toggle") || sender.hasPermission("smoothtimber.*"))) {
            sender.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + Message.COMMAND_MISSING222PERMISSION.colored(new String[] {
                "%permission%",
                "smoothtimber.toggle"
            }));
            return;
        }

        if (CutterConfig.TOGGLEABLE.isDisabled()) {
            sender.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + Message.TOGGLE_DISABLED.colored());
            return;
        }

        int time = -1;

        if (arguments.count() != 0) {
            if (arguments.getType(1) == ArgumentType.INTEGER || arguments.getType(1) == ArgumentType.BYTE
                || arguments.getType(1) == ArgumentType.SHORT) {
                time = arguments.get(1).asNumeric().asNumber().intValue();
            }
        }

        STORAGE.toggle(((Player) sender).getUniqueId(), time);

    }

    @Override
    public DefaultCompletion complete(MinecraftInfo info, Arguments arguments) {

        DefaultCompletion completion = new DefaultCompletion();

        if (arguments.count() == 0) {
            for (int time = 1; time <= 5; time++) {
                completion.add(new IntegerArgument(time * 30));
            }
        } else if (arguments.count() == 1) {
            if (arguments.getType(1) == ArgumentType.INTEGER || arguments.getType(1) == ArgumentType.BYTE
                || arguments.getType(1) == ArgumentType.SHORT) {
                NumericArgument argument = arguments.get(1).asNumeric();
                completion.add(argument);
                int number = argument.asNumber().intValue();
                for (int value = number - 50; value <= number + 50; value += 10) {
                    if (value < 1) {
                        continue;
                    }
                    completion.add(new IntegerArgument(value));
                }
            } else {
                for (int time = 1; time <= 5; time++) {
                    completion.add(new IntegerArgument(time * 30));
                }
            }
        }

        return completion;
    }

}
