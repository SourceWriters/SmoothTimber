package com.syntaxphoenix.spigot.smoothtimber.config.migration;

import com.syntaxphoenix.spigot.smoothtimber.config.ActionType;
import com.syntaxphoenix.spigot.smoothtimber.config.Migration;
import com.syntaxphoenix.spigot.smoothtimber.config.MigrationContext;

public abstract class CutterMigration extends Migration {

    public static void update1(MigrationContext context) {
        context.move("Extensions.BlockyLog", "extensions.blockylog");
        context.move("Cutter.onlyWhileSneaking", "options.cutter.sneak");
        context.move("Cutter.woodLocateRadius", "cutter.radius");
        context.move("Cutter.enablePermissions", "options.cutter.permissions");
        context.move("Cutter.AxeMaterials", "cutter.materials");
    }

    public static void update2(MigrationContext context) {
        context.map("options.cutter.sneak", boolean.class, (state) -> state ? ActionType.ON_ACTION : ActionType.DISABLED);
        context.map("options.cutter.toggleable", boolean.class, (state) -> state ? ActionType.ON_ACTION : ActionType.DISABLED);
    }

    public static void update3(MigrationContext context) {
        context.remove("extensions.blockylog");
    }

    public static void update5(MigrationContext context) {
        context.move("options.cutter.permissions", "options.cutter.permission.wood-type");
    }

    public static void update8(MigrationContext context) {
        context.move("options.cutter", "options");
    }

}
