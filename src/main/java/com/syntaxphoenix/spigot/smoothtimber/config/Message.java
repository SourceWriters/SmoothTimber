package com.syntaxphoenix.spigot.smoothtimber.config;

import com.syntaxphoenix.spigot.smoothtimber.config.config.MessageConfig;

import net.md_5.bungee.api.ChatColor;

public enum Message {

    // Global
    GLOBAL_PREFIX("&5Smooth&dTimber &8||"),
    GLOBAL_LIST222SPLIT("&7, &d"),
    GLOBAL_NOT222ALLOWED("&7You're lacking the permission &5%permission% &7to use &5Smooth&dTimber&7!"),

    // Reload
    RELOAD_NEEDED("&7Detected a %type0% change, &5reloading %type1%&7..."),
    RELOAD_DONE("&7%type% reloaded &dsuccessfully!"),

    // Version
    STARTUP_VERSION_SUPPORTED("&7You're currently using the &dsupported&7 Minecraft Version &d%minecraft% &7(&5Core %core%&7)"),
    STARTUP_VERSION_UNSUPPORTED("&7You're currently using the &cunsupported&7 Minecraft Version &4%minecraft%"),
    STARTUP_VERSION_NEED222UPDATE(
        "&7If you want to use &5Smooth&dTimber &7you need to update your server to a supported Minecraft Version"),
    STARTUP_VERSION_VERSIONS("&7Supported Versions are: &d%versions%"),

    // Types
    TYPE_MESSAGE("message"),
    TYPE_MESSAGES("messages"),
    TYPE_SETTING_MAIN("setting"),
    TYPE_SETTINGS_MAIN("settings"),
    TYPE_SETTING_ADDON("addon"),
    TYPE_SETTINGS_ADDON("addons"),
    TYPE_ADDON("%addon% setting"),
    TYPE_ADDONS("%addon% settings"),

    // Time
    TIME_SECOND("second"),
    TIME_SECONDS("seconds"),

    // Tools
    TOOLS_WOODCHOPPER("woodchopper"),

    // Toggle
    TOGGLE_ON_FOREVER("&7You enabled your &d%tool%&7!"),
    TOGGLE_ON_TIMED("&7You enabled your &d%tool% &7for &5%time%&7!"),
    TOGGLE_OFF_FOREVER("&7You disabled your &c%tool%&7!"),
    TOGGLE_OFF_TIMED("&7You disabled your &c%tool% &7for &4%time%&7!"),
    TOGGLE_DISABLED("&7Toggling is disabled!"),

    // Command
    COMMAND_ONLY_PLAYER("&7Only a &5player &7can run this command!"),
    COMMAND_WIP("&7This command is work in progress!"),
    COMMAND_NON222EXISTENT("&7This command doesn't exist! Use &5/sm help &7for help!"),
    COMMAND_MISSING222PERMISSION("&7You're lacking the permission &5%permission% &7to execute this command!"),

    // Command Description
    COMMAND_USAGE_TOGGLE("&7/sm toggle (<time>) &8- &7Enable the tree chopper permanently or a specific time in seconds."),
    COMMAND_USAGE_HELP("&7/sm help &8- &7List all smoothtimber commands with their description and usage"),

    // Abort Reasons
    ABORT_MESSAGE("&7You can't cut this tree because &c%reason%&7!"),
    ABORT_REASON_UNKNOWN("another plugin cancelled the process"),
    ABORT_REASON_GRIEFPREVENTION("it is protected by a GriefPrevetion Claim"),
    ABORT_REASON_WORLDGUARD("it is protected by a WorldGuard Region"),
    ABORT_REASON_RESIDENCE("it is protected by a Residence Region"),
    ABORT_REASON_LANDS("it is protected by a Lands Region"),
    ABORT_REASON_TOWNY("it is protected by a Towny town")

    /*
     * 
     */
    ;

    private final String message;

    /*
     * 
     */

    private Message() {
        message = "";
    }

    private Message(String message) {
        this.message = message;
    }

    /*
     * 
     */

    public final String id() {
        return name().toLowerCase().replace('_', '.').replace("222", "-");
    }

    /*
     * 
     */

    public final String message() {
        String configured = MessageConfig.MESSAGES.get(this);
        return configured != null ? configured : message;
    }

    public final String message(String[] replace) {
        return message().replace(replace[0], replace[1]);
    }

    public final String message(String[][] replace) {
        String message = message();
        for (String[] value : replace) {
            message = message.replace(value[0], value[1]);
        }
        return message;
    }

    /*
     * 
     */

    public final String colored() {
        return ChatColor.translateAlternateColorCodes('&', message());
    }

    public final String colored(String[] replace) {
        return ChatColor.translateAlternateColorCodes('&', message(replace));
    }

    public final String colored(String[][] replace) {
        return ChatColor.translateAlternateColorCodes('&', message(replace));
    }

    /*
     * 
     */

    public static final Message fromId(String id) {
        return valueOf(id.replace("-", "222").replace('.', '_').toUpperCase());
    }

}
