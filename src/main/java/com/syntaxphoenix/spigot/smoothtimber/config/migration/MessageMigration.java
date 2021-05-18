package com.syntaxphoenix.spigot.smoothtimber.config.migration;

import com.syntaxphoenix.spigot.smoothtimber.config.Migration;
import com.syntaxphoenix.spigot.smoothtimber.config.MigrationContext;

public abstract class MessageMigration extends Migration {

    public static void update1(MigrationContext context) {
        context.move(ofEnum("TOGGLE_OFF"), ofEnum("TOGGLE_OFF_FOREVER"));
        context.move(ofEnum("VERSION_SUPPORTED"), ofEnum("STARTUP_VERSION_SUPPORTED"));
        context.move(ofEnum("VERSION_UNSUPPORTED"), ofEnum("STARTUP_VERSION_UNSUPPORTED"));
        context.move(ofEnum("VERSION_NEED222UPDATE"), ofEnum("STARTUP_VERSION_NEED222UPDATE"));
        context.move(ofEnum("VERSION_VERSIONS"), ofEnum("STARTUP_VERSION_VERSIONS"));
    }

    public static void update2(MigrationContext context) {
        context.stack("messages", ofEnum("GLOBAL_PREFIX"));
        context.stack("messages", ofEnum("GLOBAL_LIST222SPLIT"));
        context.stack("messages", ofEnum("RELOAD_NEEDED"));
        context.stack("messages", ofEnum("RELOAD_DONE"));
        context.stack("messages", ofEnum("STARTUP_VERSION_SUPPORTED"));
        context.stack("messages", ofEnum("STARTUP_VERSION_UNSUPPORTED"));
        context.stack("messages", ofEnum("STARTUP_VERSION_NEED222UPDATE"));
        context.stack("messages", ofEnum("STARTUP_VERSION_VERSIONS"));
        context.stack("messages", ofEnum("TYPE_MESSAGE"));
        context.stack("messages", ofEnum("TYPE_MESSAGES"));
        context.stack("messages", ofEnum("TYPE_SETTING"));
        context.stack("messages", ofEnum("TYPE_SETTINGS"));
        context.stack("messages", ofEnum("TIME_SECOND"));
        context.stack("messages", ofEnum("TIME_SECONDS"));
        context.stack("messages", ofEnum("TOOLS_WOODCHOPPER"));
        context.stack("messages", ofEnum("TOGGLE_ON_FOREVER"));
        context.stack("messages", ofEnum("TOGGLE_ON_TIMED"));
        context.stack("messages", ofEnum("TOGGLE_OFF_FOREVER"));
        context.stack("messages", ofEnum("TOGGLE_OFF_TIMED"));
        context.stack("messages", ofEnum("TOGGLE_DISABLED"));
        context.stack("messages", ofEnum("COMMAND_ONLY_PLAYER"));
        context.stack("messages", ofEnum("COMMAND_WIP"));
        context.stack("messages", ofEnum("COMMAND_NON222EXISTENT"));
        context.stack("messages", ofEnum("COMMAND_MISSING222PERMISSION"));
        context.stack("messages", ofEnum("COMMAND_USAGE_TOGGLE"));
        context.stack("messages", ofEnum("COMMAND_USAGE_HELP"));
        context.stack("messages", ofEnum("ABORT_MESSAGE"));
        context.stack("messages", ofEnum("ABORT_REASON_UNKNOWN"));
        context.stack("messages", ofEnum("ABORT_REASON_WORLDGUARD"));
    }

    public static void update4(MigrationContext context) {
        context.move(enumPath("messages", "TYPE_SETTING"), ofEnum("TYPE_SETTING_MAIN"));
        context.move(enumPath("messages", "TYPE_SETTINGS"), ofEnum("TYPE_SETTINGS_MAIN"));
    }

}
