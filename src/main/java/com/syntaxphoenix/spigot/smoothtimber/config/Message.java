package com.syntaxphoenix.spigot.smoothtimber.config;

import net.md_5.bungee.api.ChatColor;

public enum Message {

	// Global
	GLOBAL_PREFIX("&5Smooth&dTimber &8||"),
	GLOBAL_LIST222SPLIT("&7, &d"),

	// Reload
	RELOAD_NEEDED("&7Detected a %type0% change, &5reloading %type1%&7..."),
	RELOAD_DONE("&7%type% reloaded &dsuccessfully!"),
	
	// Version
	VERSION_SUPPORTED("&7You're currently using the &dsupported&7 Minecraft Version &d%minecraft% &7(&5Core %core%&7)"),
	VERSION_UNSUPPORTED("&7You're currently using the &cunsupported&7 Minecraft Version &4%minecraft%"),
	VERSION_NEED222UPDATE("&7If you want to use &5Smooth&dTimber &7you need to update your server to a supported Minecraft Version"),
	VERSION_VERSIONS("&7Supported Versions are: &d%versions%"),

	// Types
	TYPE_MESSAGE("message"), TYPE_MESSAGES("messages"), TYPE_SETTING("setting"), TYPE_SETTINGS("settings"),

	// Time
	TIME_SECOND("second"), TIME_SECONDS("seconds"),

	// Tools
	TOOLS_WOODCHOPPER("woodchopper"),

	// Toggle
	TOGGLE_ON_FOREVER("&7You enabled your &d%tool%&7!"),
	TOGGLE_ON_TIMED("&7You enabled your &d%tool% &7for &2%time%&7!"),
	TOGGLE_OFF("&7You disabled your &c%tool%&7!"),
	TOGGLE_DISABLED("&7Toggling is disabled!"),
	
	// Command
	COMMAND_ONLY_PLAYER("&7Only a &5player &7can run this command!"), 
	COMMAND_WIP("&7This command is work in progress!"), 
	COMMAND_NON222EXISTENT("&7This command doesn't exist!"),
	COMMAND_MISSING222PERMISSION("&7You're lacking the permission &5%permission% &7to execute this command!"),
	
	// Command Description
	COMMAND_USAGE_TOGGLE("&7/sm toggle (<time>) &8- &7Enable the tree chopper permanently or a specific time in seconds."),
	COMMAND_USAGE_HELP("&7/sm help &8- &7List all smoothtimber commands with their description and usage")

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
		for (String[] value : replace)
			message = message.replace(value[0], value[1]);
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
