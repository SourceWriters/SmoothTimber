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
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKey;
import net.sourcewriters.smoothtimber.api.util.ExtensionHelper;
import net.sourcewriters.smoothtimber.api.util.IResource;

public abstract class SmoothTimberModule extends Module {

    private static final Predicate<String> COMMAND_NAME = Pattern.compile("[\\da-z_]+").asMatchPredicate();

    protected final SmoothTimberApi api = SmoothTimberApi.get();
    protected final ISmoothTimberCore core = api.getCore();

    protected final ILogger logger = core.getLogger(this);
    protected final IResource resource = core.getResource(this);

    /**
     * Enables the module and registers everything that can be registered
     */
    @Override
    public final void enable() throws Exception {
        onEnable();
        registerCommands();
        registerListeners();
    }

    /**
     * Disables the module and unregisters everything that is registered
     */
    @Override
    public final void disable() throws Exception {
        unregisterListeners();
        unregisterCommands();
        onDisable();
    }

    /**
     * Is executed on module enable
     */
    protected void onEnable() {}

    /**
     * Is executed on module disable
     */
    protected void onDisable() {}

    /**
     * Gets the logger of this module
     * 
     * @return the module logger
     */
    public final ILogger getLogger() {
        return logger;
    }

    /**
     * Gets the resource of this module
     * 
     * @return the module resource
     */
    public final IResource getResource() {
        return resource;
    }

    /**
     * Gets a module namespaced key from the api key cache
     * 
     * @param  name the name of the key
     * 
     * @return      the namespaced key
     */
    public final ResourceKey key(final String name) {
        return api.getKeyCache().get(this, name);
    }

    /*
     * Internals
     */

    private final ArrayList<String> commands = new ArrayList<>();

    private final void registerCommands() {
        logger.log(LogTypeId.INFO, "Registering commands...");
        final CommandManager<PlatformSource> commandManager = api.getCommandManager();
        final List<IPlatformCommand> providers = getWrapper().getManager().getExtensionManager().getExtensionsOf(getId(),
            IPlatformCommand.class);
        if (providers.isEmpty()) {
            logger.log(LogTypeId.INFO, "No commands to register.");
            return;
        }
        int count = 0;
        for (final IPlatformCommand provider : providers) {
            final Optional<Command> option = ExtensionHelper.getAnnotation(provider.getClass(), Command.class);
            if (option.isEmpty()) {
                logger.log(LogTypeId.ERROR, "Couldn't find @Command at '" + provider.getClass().getSimpleName() + "'!");
                continue;
            }
            final Command command = option.get();
            if (!COMMAND_NAME.test(command.name())) {
                logger.log(LogTypeId.ERROR, "Invalid command name '" + command.name() + "'!");
                continue;
            }
            final List<String> list = Arrays.asList(command.alias());
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
            } catch (final Exception exception) {
                logger.log(LogTypeId.ERROR, "Failed to create command instance of '" + command.name() + "'!");
                logger.log(LogTypeId.ERROR, exception);
                continue;
            }
            final CommandState state = commandManager.register(node, list.toArray(String[]::new));
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

    private final void unregisterCommands() {
        logger.log(LogTypeId.INFO, "Unregistering commands...");
        if (commands.isEmpty()) {
            logger.log(LogTypeId.INFO, "No commands to unregister.");
            return;
        }
        final CommandManager<PlatformSource> commandManager = api.getCommandManager();
        for (final String command : commands) {
            commandManager.unregister(command);
        }
        commands.clear();
        logger.log(LogTypeId.INFO, "Unregistered all commands");
    }

    private final void registerListeners() {
        logger.log(LogTypeId.INFO, "Registering listeners...");
        final int[] status = api.getEventManager().getHandler().register(this);
        if (status.length == 0) {
            logger.log(LogTypeId.INFO, "No listeners to register.");
            return;
        }
        logger.log(LogTypeId.INFO, "Registered " + status[0] + " of " + status[1] + " listeners!");
    }

    private final void unregisterListeners() {
        logger.log(LogTypeId.INFO, "Unregistering listeners...");
        api.getEventManager().getHandler().unregister(this);
        logger.log(LogTypeId.INFO, "Unregistered all listeners");
    }

}
