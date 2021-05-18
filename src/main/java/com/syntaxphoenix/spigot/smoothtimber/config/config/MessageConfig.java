package com.syntaxphoenix.spigot.smoothtimber.config.config;

import java.io.File;
import java.util.EnumMap;

import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.config.STConfig;
import com.syntaxphoenix.spigot.smoothtimber.config.migration.MessageMigration;

public final class MessageConfig extends STConfig {

    public static final MessageConfig INSTANCE = new MessageConfig();

    public static EnumMap<Message, String> MESSAGES = new EnumMap<>(Message.class);

    /*
     * 
     */

    private MessageConfig() {
        super(new File("plugins/SmoothTimber", "message.yml"), MessageMigration.class, 4);
    }

    /*
     * Type
     */

    @Override
    protected String getSingleType() {
        return Message.TYPE_MESSAGE.message();
    }

    @Override
    protected String getMultipleType() {
        return Message.TYPE_MESSAGES.message();
    }

    /*
     * Handle
     */

    @Override
    protected void onSetup() {

    }

    @Override
    protected void onLoad() {
        for (Message message : Message.values()) {
            MESSAGES.put(message, check("messages." + message.id(), message.message()));
        }
    }

    @Override
    protected void onUnload() {

    }

}
