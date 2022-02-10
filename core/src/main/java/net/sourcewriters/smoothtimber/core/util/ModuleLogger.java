package net.sourcewriters.smoothtimber.core.util;

import java.util.function.BiConsumer;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;
import com.syntaxphoenix.syntaxapi.logging.LoggerState;
import com.syntaxphoenix.syntaxapi.logging.color.LogType;
import com.syntaxphoenix.syntaxapi.logging.color.LogTypeMap;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;

public final class ModuleLogger implements ILogger {

    private final JavaLogger delegate;
    private final String module;

    public ModuleLogger(JavaLogger delegate, SmoothTimberModule module) {
        this.delegate = delegate;
        this.module = module.getId();
    }

    @Override
    public ILogger close() {
        return this;
    }

    @Override
    public BiConsumer<Boolean, String> getCustom() {
        throw new UnsupportedOperationException("Only delegate");
    }

    @Override
    public LoggerState getState() {
        return delegate.getState();
    }

    @Override
    public String getThreadName() {
        throw new UnsupportedOperationException("Only delegate");
    }

    @Override
    public LogType getType(String name) {
        return delegate.getType(name);
    }

    @Override
    public LogTypeMap getTypeMap() {
        throw new UnsupportedOperationException("Only delegate");
    }

    @Override
    public boolean isColored() {
        return delegate.isColored();
    }

    @Override
    public ILogger log(String message) {
        return log(LogTypeId.INFO, message);
    }

    @Override
    public ILogger log(String... messages) {
        return log(LogTypeId.INFO, messages);
    }

    @Override
    public ILogger log(Throwable throwable) {
        return log(LogTypeId.ERROR, throwable);
    }

    @Override
    public ILogger log(LogTypeId type, String message) {
        return log(type.id(), message);
    }

    @Override
    public ILogger log(LogTypeId type, String... messages) {
        return log(type.id(), messages);
    }

    @Override
    public ILogger log(LogTypeId type, Throwable throwable) {
        return log(type.id(), throwable);
    }

    @Override
    public ILogger log(String type, String message) {
        delegate.printModule(type, module, message);
        return this;
    }

    @Override
    public ILogger log(String type, String... messages) {
        delegate.printModule(type, module, messages);
        return this;
    }

    @Override
    public ILogger log(String type, Throwable throwable) {
        delegate.printModule(type, module, throwable);
        return this;
    }

    @Override
    public ILogger setColored(boolean paramBoolean) {
        throw new UnsupportedOperationException("Only delegate");
    }

    @Override
    public ILogger setCustom(BiConsumer<Boolean, String> paramBiConsumer) {
        throw new UnsupportedOperationException("Only delegate");
    }

    @Override
    public ILogger setState(LoggerState paramLoggerState) {
        throw new UnsupportedOperationException("Only delegate");
    }

    @Override
    public ILogger setThreadName(String paramString) {
        throw new UnsupportedOperationException("Only delegate");
    }

    @Override
    public ILogger setType(LogType paramLogType) {
        throw new UnsupportedOperationException("Only delegate");
    }

}
