package com.syntaxphoenix.spigot.smoothtimber.command;

import com.syntaxphoenix.syntaxapi.command.Arguments;
import com.syntaxphoenix.syntaxapi.command.BaseCommand;
import com.syntaxphoenix.syntaxapi.command.BaseInfo;
import com.syntaxphoenix.syntaxapi.command.DefaultCompletion;

public abstract class SmoothCommand extends BaseCommand {

    @Override
    public final void execute(final BaseInfo info, final Arguments arguments) {
        if (info instanceof MinecraftInfo) {
            execute((MinecraftInfo) info, arguments);
        }
    }

    public abstract void execute(MinecraftInfo info, Arguments arguments);

    public abstract DefaultCompletion complete(MinecraftInfo info, Arguments arguments);

}
