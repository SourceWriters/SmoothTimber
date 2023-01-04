package com.syntaxphoenix.spigot.smoothtimber.config;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;

public final class ConfigTimer implements Runnable {

    public static final ConfigTimer TIMER = new ConfigTimer();

    private final ArrayList<STConfig> reload = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final ReadLock read = lock.readLock();
    private final WriteLock write = lock.writeLock();

    private ConfigTimer() {

    }

    public boolean load(final STConfig config) {
        boolean output;
        read.lock();
        if (output = !reload.contains(config)) {
            read.unlock();
            write.lock();
            try {
                reload.add(config);
            } finally {
                write.unlock();
            }
        }
        return output;
    }

    public boolean unload(final STConfig config) {
        boolean output;
        read.lock();
        if (output = reload.contains(config)) {
            read.unlock();
            write.lock();
            try {
                reload.remove(config);
            } finally {
                write.unlock();
            }
        }
        return output;
    }

    @Override
    public void run() {
        read.lock();
        try {
            for (final STConfig config : reload) {
                if (config.loaded < config.file.lastModified()) {
                    final String multiple = config.getMultipleType();
                    PluginUtils.sendConsoleMessage(false,
                        Message.GLOBAL_PREFIX.colored() + ' ' + Message.RELOAD_NEEDED.colored(new String[][] {
                            {
                                "%type0%",
                                config.getSingleType()
                            },
                            {
                                "%type1%",
                                multiple
                            }
                        }));
                    config.reload();
                    PluginUtils.sendConsoleMessage(false, Message.GLOBAL_PREFIX.colored() + ' ' + Message.RELOAD_DONE.colored(new String[] {
                        "%type%",
                        multiple
                    }));
                }
            }
        } finally {
            read.unlock();
        }
    }

}
