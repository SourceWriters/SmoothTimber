package net.sourcewriters.smoothtimber.core;

import java.util.function.Consumer;

import com.syntaxphoenix.avinity.module.ModuleManager;
import com.syntaxphoenix.syntaxapi.event.EventManager;
import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;

import net.sourcewriters.smoothtimber.api.ISmoothTimberPermissionManager;
import net.sourcewriters.smoothtimber.api.ISmoothTimberCore;
import net.sourcewriters.smoothtimber.api.SmoothTimberApi;
import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlatform;
import net.sourcewriters.smoothtimber.api.util.IResource;
import net.sourcewriters.smoothtimber.core.util.JavaLogger;
import net.sourcewriters.smoothtimber.core.util.ModuleLogger;
import net.sourcewriters.smoothtimber.core.util.ResourceImpl;

public final class SmoothTimberCore implements ISmoothTimberCore {

    private final SmoothTimberApi api;
    private final ResourceImpl resource;
    private final ModuleManager<SmoothTimberModule> moduleManager;

    private final JavaLogger logger;

    private final SmoothTimberModule coreModule;
    private final SmoothTimberCoreRegistry registry = new SmoothTimberCoreRegistry();

    public SmoothTimberCore(final Consumer<String> logDelegate, final ISmoothTimberPlatform platform) {
        this.logger = new JavaLogger(logDelegate);
        this.api = new SmoothTimberApi(this, platform);
        this.resource = new ResourceImpl(logger, platform.getPlugin());
        this.moduleManager = new ModuleManager<>(SmoothTimberModule.class, new EventManager(logger),
            platform.getPlugin().getSystemVersion());
        moduleManager.getEventManager().registerEvents(new ModuleListener(this));
        this.coreModule = new SmoothTimberCoreModule(moduleManager.getSystem());
    }

    /*
     * Module redirect
     */

    public boolean enable() {
        try {
            coreModule.enable();
        } catch (Exception exp) {
            logger.log(LogTypeId.ERROR, exp);
            return false;
        }
        return true;
    }

    public boolean disable() {
        try {
            coreModule.disable();
        } catch (Exception exp) {
            logger.log(LogTypeId.ERROR, exp);
            return false;
        }
        return true;
    }

    /*
     * Impl
     */

    public ModuleManager<SmoothTimberModule> getModuleManager() {
        return moduleManager;
    }

    public EventManager getModuleEventManager() {
        return moduleManager.getEventManager();
    }

    public SmoothTimberApi getApi() {
        return api;
    }

    @Override
    public ILogger getSystemLogger() {
        return logger;
    }

    @Override
    public IResource getSystemResource() {
        return resource;
    }

    @Override
    public ILogger getLogger(final SmoothTimberModule module) {
        if (module.getLogger() == null) {
            if (module instanceof SmoothTimberCoreModule) {
                return logger;
            }
            return new ModuleLogger(logger, module);
        }
        return module.getLogger();
    }

    @Override
    public IResource getResource(final SmoothTimberModule module) {
        if (module.getResource() == null) {
            if (module instanceof SmoothTimberCoreModule) {
                return resource;
            }
            return new ResourceImpl(module);
        }
        return module.getResource();
    }

    @Override
    public SmoothTimberCoreRegistry getRegistry() {
        return registry;
    }

    @Override
    public ISmoothTimberPermissionManager getPermissionManager() {
        // TODO: Implement permission manager
        return null;
    }

}
