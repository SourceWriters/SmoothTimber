package net.sourcewriters.smoothtimber.api.platform.event.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sourcewriters.smoothtimber.api.platform.event.PlatformEvent;

final class PlatformEventExecutor {

    private final Object instance;
    private final Method target;

    private final Class<? extends PlatformEvent> eventType;

    private final boolean hasArgument;
    private final boolean ignoreCancelled;
    private final PlatformEventPriority priority;

    public PlatformEventExecutor(final Class<? extends PlatformEvent> eventType, final Object instance, final Method target,
        final PlatformEventListener listener) {
        this.eventType = eventType;
        this.instance = instance;
        this.target = target;
        this.hasArgument = target.getParameterCount() == 1;
        this.ignoreCancelled = listener.ignoreCancelled();
        this.priority = listener.priority();
    }

    public Object getInstance() {
        return instance;
    }

    public boolean doesIgnoreCancelled() {
        return ignoreCancelled;
    }

    public PlatformEventPriority getPriority() {
        return priority;
    }

    public Class<? extends PlatformEvent> getEventType() {
        return eventType;
    }

    public void call(final PlatformEvent event) {
        if (!eventType.isAssignableFrom(event.getClass())) {
            return;
        }
        try {
            if (hasArgument) {
                target.invoke(instance, eventType.cast(event));
                return;
            }
            target.invoke(instance);
        } catch (ClassCastException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exp) {
            throw new PlatformEventException(
                "Failed to invoke '" + target.getName() + "' on instance '" + instance.getClass().getSimpleName() + "'!", exp);
        } catch (final Exception exp) {
            if (exp instanceof PlatformEventException) {
                throw exp;
            }
            throw new PlatformEventException(
                "Failed to execute event on '" + instance.getClass().getSimpleName() + ':' + target.getName() + "'!", exp);
        }
    }

}
