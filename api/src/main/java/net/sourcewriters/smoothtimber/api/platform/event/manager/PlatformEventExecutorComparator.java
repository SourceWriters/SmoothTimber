package net.sourcewriters.smoothtimber.api.platform.event.manager;

import java.util.Comparator;

final class PlatformEventExecutorComparator implements Comparator<PlatformEventExecutor> {

    public static final PlatformEventExecutorComparator COMPARATOR = new PlatformEventExecutorComparator();

    private PlatformEventExecutorComparator() {}

    @Override
    public int compare(final PlatformEventExecutor o1, final PlatformEventExecutor o2) {
        return Integer.compare(o1.getPriority().ordinal(), o2.getPriority().ordinal()) * -1;
    }

}
