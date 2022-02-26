package net.sourcewriters.smoothtimber.api;

import com.syntaxphoenix.avinity.command.CommandManager;
import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.smoothtimber.api.data.json.JsonAdapterRegistry;
import net.sourcewriters.smoothtimber.api.data.json.JsonContainer;
import net.sourcewriters.smoothtimber.api.data.json.JsonFactory;
import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlatform;
import net.sourcewriters.smoothtimber.api.platform.command.PlatformSource;
import net.sourcewriters.smoothtimber.api.platform.event.manager.PlatformEventManager;
import net.sourcewriters.smoothtimber.api.resource.key.ResourceKeyCache;

public final class SmoothTimberApi {

    private static final Container<SmoothTimberApi> API = Container.of();

    public static SmoothTimberApi get() {
        return API.get();
    }

    private final ISmoothTimberCore core;
    private final ISmoothTimberPlatform platform;
    private final PlatformEventManager eventManager;
    private final CommandManager<PlatformSource> commandManager;

    private final JsonAdapterRegistry dataRegistry = new JsonAdapterRegistry();
    private final JsonFactory dataFactory = new JsonFactory(dataRegistry);

    private final ResourceKeyCache keyCache = new ResourceKeyCache();
    private final JsonContainer configuration = new JsonContainer(dataRegistry);

    /**
     * Constructs the core of SmoothTimber
     * 
     * @param platform the platform specific adapter
     */
    public SmoothTimberApi(final ISmoothTimberCore core, final ISmoothTimberPlatform platform) {
        if (API.isPresent()) {
            throw new IllegalStateException("SmoothTimberApi is already present!");
        }
        API.replace(this).lock();
        this.core = core;
        this.platform = platform;
        this.commandManager = new CommandManager<>();
        this.eventManager = new PlatformEventManager(platform.getEventAdapter(), platform.getExecutor());
    }

    /**
     * Gets the core of SmoothTimber
     * 
     * @return the core
     */
    public ISmoothTimberCore getCore() {
        return core;
    }

    /**
     * Gets the platform specific adapter
     * 
     * @return the platform adapter
     */
    public ISmoothTimberPlatform getPlatform() {
        return platform;
    }

    /**
     * Gets the JSON data registry
     * 
     * @return the data registry
     */
    public JsonAdapterRegistry getDataRegistry() {
        return dataRegistry;
    }

    /**
     * Gets the JSON data factory
     * 
     * @return the data factory
     */
    public JsonFactory getDataFactory() {
        return dataFactory;
    }

    /**
     * Gets the JSON configuration
     * 
     * @return the configuration
     */
    public JsonContainer getConfiguration() {
        return configuration;
    }

    /**
     * Gets the resource key cache
     * 
     * @return the cache
     */
    public ResourceKeyCache getKeyCache() {
        return keyCache;
    }

    /**
     * Gets the internal event manager
     * 
     * @return the event manager
     */
    public PlatformEventManager getEventManager() {
        return eventManager;
    }

    /**
     * Gets the internal command manager
     * 
     * @return the command manager
     */
    public CommandManager<PlatformSource> getCommandManager() {
        return commandManager;
    }

}
