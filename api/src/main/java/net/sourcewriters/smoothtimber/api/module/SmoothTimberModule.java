package net.sourcewriters.smoothtimber.api.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import com.syntaxphoenix.avinity.command.CommandManager;
import com.syntaxphoenix.avinity.command.CommandState;
import com.syntaxphoenix.avinity.command.node.RootNode;
import com.syntaxphoenix.avinity.module.Module;
import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;

import net.sourcewriters.smoothtimber.api.ISmoothTimberCore;
import net.sourcewriters.smoothtimber.api.SmoothTimberApi;
import net.sourcewriters.smoothtimber.api.platform.command.Command;
import net.sourcewriters.smoothtimber.api.platform.command.IPlatformCommand;
import net.sourcewriters.smoothtimber.api.platform.command.PlatformSource;
import net.sourcewriters.smoothtimber.api.util.ExtensionHelper;
import net.sourcewriters.smoothtimber.api.util.IResource;

public abstract class SmoothTimberModule extends Module {

    private static final Predicate<String> COMMAND_NAME = Pattern.compile("[\\da-z_]+").asMatchPredicate();

    protected final SmoothTimberApi api = SmoothTimberApi.get();
    protected final ISmoothTimberCore core = api.getCore();

    protected final ILogger logger = core.getLogger(this);
    protected final IResource resource = core.getResource(this);

    @Override
    public final void enable() throws Exception {
        onEnable();
        registerCommands();
        registerListeners();
    }

    @Override
    public final void disable() throws Exception {
        unregisterListeners();
        unregisterCommands();
        onDisable();
    }

    public void onEnable() {}

    public void onDisable() {}

    public final ILogger getLogger() {
        return logger;
    }

    public final IResource getResource() {
        return resource;
    }

    /*
     * Internals
     */

    private final ArrayList<String> commands = new ArrayList<>();

    private void registerCommands() {
        logger.log(LogTypeId.INFO, "Registering commands...");
        CommandManager<PlatformSource> commandManager = api.getCommandManager();
        List<IPlatformCommand> providers = getWrapper().getManager().getExtensionManager().getExtensionsOf(getId(), IPlatformCommand.class);
        if (providers.isEmpty()) {
            logger.log(LogTypeId.INFO, "No commands to register.");
            return;
        }
        int count = 0;
        for (IPlatformCommand provider : providers) {
            Optional<Command> option = ExtensionHelper.getAnnotation(provider.getClass(), Command.class);
            if (option.isEmpty()) {
                logger.log(LogTypeId.ERROR, "Couldn't find @Command at '" + provider.getClass().getSimpleName() + "'!");
                continue;
            }
            Command command = option.get();
            if (!COMMAND_NAME.test(command.name())) {
                logger.log(LogTypeId.ERROR, "Invalid command name '" + command.name() + "'!");
                continue;
            }
            List<String> list = Arrays.asList(command.alias());
            if (!list.isEmpty()) {
                for (int idx = 0; idx < list.size(); idx++) {
                    if (COMMAND_NAME.test(list.get(idx))) {
                        continue;
                    }
                    logger.log(LogTypeId.WARNING, "Invalid command alias '" + list.get(idx) + "' of command '" + command.name() + "'!");
                    list.remove(idx--);
                }
            }
            RootNode<PlatformSource> node;
            try {
                node = provider.build(command.name());
            } catch (Exception exception) {
                logger.log(LogTypeId.ERROR, "Failed to create command instance of '" + command.name() + "'!");
                logger.log(LogTypeId.ERROR, exception);
                continue;
            }
            CommandState state = commandManager.register(node, list.toArray(String[]::new));
            if (state == CommandState.FAILED) {
                logger.log(LogTypeId.ERROR, "Failed to register command '" + command.name() + "'!");
                continue;
            }
            count++;
            commands.add(command.name());
            commands.addAll(list);
            if (state == CommandState.PARTIAL) {
                logger.log(LogTypeId.WARNING, "Only registered aliases " + list.toString() + " of command '" + command.name() + "'!");
                continue;
            }
        }
        logger.log(LogTypeId.INFO, "Registered " + count + " commands of " + providers.size() + "!");
    }

    private void unregisterCommands() {
        logger.log(LogTypeId.INFO, "Unregistering commands...");
        if (commands.isEmpty()) {
            logger.log(LogTypeId.INFO, "No commands to unregister.");
            return;
        }
        CommandManager<PlatformSource> commandManager = api.getCommandManager();
        for (String command : commands) {
            commandManager.unregister(command);
        }
        commands.clear();
        logger.log(LogTypeId.INFO, "Unregistered all commands");
    }

    private void registerListeners() {
        logger.log(LogTypeId.INFO, "Registering listeners...");
        int[] status = api.getEventManager().getHandler().register(this);
        if (status.length == 0) {
            logger.log(LogTypeId.INFO, "No listeners to register.");
            return;
        }
        logger.log(LogTypeId.INFO, "Registered " + status[0] + " of " + status[1] + " listeners!");
    }

    private void unregisterListeners() {
        logger.log(LogTypeId.INFO, "Unregistering listeners...");
        api.getEventManager().getHandler().unregister(this);
        logger.log(LogTypeId.INFO, "Unregistered all listeners");
    }

}
