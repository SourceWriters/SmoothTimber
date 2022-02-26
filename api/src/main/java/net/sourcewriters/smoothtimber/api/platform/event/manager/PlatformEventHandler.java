package net.sourcewriters.smoothtimber.api.platform.event.manager;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.platform.event.IPlatformCancelable;
import net.sourcewriters.smoothtimber.api.platform.event.PlatformEvent;

public final class PlatformEventHandler {

    private final HashMap<Class<? extends PlatformEvent>, ArrayList<PlatformEventExecutor>> executors = new HashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    private ArrayList<PlatformEventExecutor> executorsOf(final Class<? extends PlatformEvent> eventType) {
        return executors.computeIfAbsent(eventType, ignore -> new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    public void unregister(final SmoothTimberModule module) {
        final Class<? extends PlatformEvent>[] eventTypes = executors.keySet().toArray(Class[]::new);
        for (final Class<? extends PlatformEvent> eventType : eventTypes) {
            final ArrayList<PlatformEventExecutor> list = executors.get(eventType);
            if (list == null || list.isEmpty()) {
                if (executors.containsKey(eventType)) {
                    lock.lock();
                    executors.remove(eventType);
                    lock.unlock();
                }
                continue;
            }
            for (int index = 0; index < list.size(); index++) {
                final PlatformEventExecutor executor = list.get(index);
                if (module.getWrapper().isFromModule(executor.getInstance().getClass())) {
                    continue;
                }
                lock.lock();
                list.remove(index);
                index--;
                lock.unlock();
            }
        }
    }

    public int[] register(final SmoothTimberModule module) {
        final List<IPlatformEventListener> listeners = module.getModuleManager().getExtensionManager().getExtensionsOf(module.getId(),
            IPlatformEventListener.class);
        if (listeners.isEmpty()) {
            return new int[] {
                0,
                0
            };
        }
        int count = 0;
        for (final IPlatformEventListener listener : listeners) {
            if (register(listener)) {
                count++;
                continue;
            }
        }
        return new int[] {
            count,
            listeners.size()
        };
    }

    public boolean register(final IPlatformEventListener instance) {
        final Class<?> clazz = instance.getClass();
        final HashSet<Method> methods = new HashSet<>();
        Collections.addAll(methods, clazz.getMethods());
        Collections.addAll(methods, clazz.getDeclaredMethods());
        boolean found = false;
        for (final Method method : methods) {
            final int mod = method.getModifiers();
            if (!Modifier.isPublic(mod) || Modifier.isStatic(mod)) {
                continue;
            }
            if (register(instance, method)) {
                found = true;
            }
        }
        return found;
    }

    private boolean register(final Object instance, final Method method) {
        final PlatformEventListener listener = method.getAnnotation(PlatformEventListener.class);
        if (listener == null || method.getParameterCount() > 1) {
            return false;
        }
        final Class<?> rawType = method.getParameterCount() == 0 || listener.eventType() != PlatformEvent.class ? listener.eventType()
            : method.getParameters()[0].getType();
        if (!PlatformEvent.class.isAssignableFrom(rawType)) {
            return false;
        }
        final Class<? extends PlatformEvent> eventType = rawType.asSubclass(PlatformEvent.class);
        executorsOf(eventType).add(new PlatformEventExecutor(eventType, instance, method, listener));
        return true;
    }

    private ArrayList<PlatformEventExecutor> getExecutorsFor(final Class<? extends PlatformEvent> eventType) {
        final ArrayList<PlatformEventExecutor> list = new ArrayList<>();
        lock.lock();
        for (final Class<? extends PlatformEvent> target : executors.keySet()) {
            if (target.isAssignableFrom(eventType)) {
                list.addAll(executors.get(target));
            }
        }
        lock.unlock();
        return list;
    }

    public void call(final PlatformEvent event) {
        final ArrayList<PlatformEventExecutor> list = getExecutorsFor(event.getClass());
        if (list.isEmpty()) {
            return;
        }
        list.sort(PlatformEventExecutorComparator.COMPARATOR);
        if (event instanceof IPlatformCancelable) {
            final IPlatformCancelable cancel = (IPlatformCancelable) event;
            for (final PlatformEventExecutor executor : list) {
                if (!executor.doesIgnoreCancelled() && cancel.isCancelled()) {
                    continue;
                }
                executor.call(event);
            }
            return;
        }
        for (final PlatformEventExecutor executor : list) {
            executor.call(event);
        }
    }

}
