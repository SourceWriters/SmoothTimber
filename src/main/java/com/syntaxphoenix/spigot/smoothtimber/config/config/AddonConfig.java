package com.syntaxphoenix.spigot.smoothtimber.config.config;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityHandler;
import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.config.STConfig;
import com.syntaxphoenix.spigot.smoothtimber.config.migration.AddonMigration;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;

public final class AddonConfig extends STConfig {

    public static final AddonConfig INSTANCE = new AddonConfig();

    /*
     * 
     */

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ArrayList<String> disabled = new ArrayList<>();

    private final ReadLock read = lock.readLock();
    private final WriteLock write = lock.writeLock();

    private boolean skip = true;

    private AddonConfig() {
        super(new File("plugins/SmoothTimber", "addons.yml"), AddonMigration.class, 1);
    }

    /*
     * Type
     */

    @Override
    protected String getSingleType() {
        return Message.TYPE_SETTING_ADDON.message();
    }

    @Override
    protected String getMultipleType() {
        return Message.TYPE_SETTINGS_ADDON.message();
    }

    /*
     * Handle
     */

    @Override
    protected void onSetup() {
        load(false);
    }

    @Override
    protected void onLoad() {
        if (skip) {
            skip = false;
            return;
        }
        load(true);
    }

    @Override
    protected void onUnload() {

    }

    private void load(final boolean refresh) {
        write.lock();
        try {
            disabled.clear();
            for (final String compat : CompatibilityHandler.getCompatibilityNames()) {
                if (check(compat)) {
                    disabled.add(compat);
                }
            }
            read.lock();
        } finally {
            write.unlock();
        }
        try {
            if (refresh) {
                CompatibilityHandler.handleSettingsUpdate(PluginUtils.SETTINGS);
            }
        } finally {
            read.unlock();
        }
    }

    /*
     * Method
     */

    private boolean check(final String compat) {
        return !check("addons." + compat, true);
    }

    public boolean isDisabled(final String name) {
        read.lock();
        try {
            return this.disabled.contains(name);
        } finally {
            read.unlock();
        }
    }

}
