package com.syntaxphoenix.spigot.smoothtimber.compatibility.placeholderapi;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PlayerState;
import com.syntaxphoenix.spigot.smoothtimber.utilities.limit.Limiter;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class SmoothTimberPlaceholders extends PlaceholderExpansion {

    private String authors;
    private String version;
    private boolean enabled = false;

    protected void enable(final PluginDescriptionFile description) {
        enabled = true;
        authors = Strings.toString(description.getAuthors(), ", ");
        version = description.getVersion();
        register();
    }

    protected void disable() {
        enabled = false;
        unregister();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String getAuthor() {
        return authors;
    }

    @Override
    public String getIdentifier() {
        return "smoothtimber";
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String onPlaceholderRequest(final Player player, final String identifier) {
        if (!enabled) {
            return null;
        }
        final Object value = player == null ? getWithoutPlayer(identifier) : getWithPlayer(player, identifier);
        if (value == null) {
            return null;
        }
        return value instanceof String ? (String) value : value.toString();
    }

    public Object getWithoutPlayer(final String identifier) {
        switch (identifier) {
        case "cutter_radius":
            return CutterConfig.CHECK_RADIUS;
        case "cutter_depth":
            return CutterConfig.ROOT_DEPTH;
        case "enchantment_unbreaking_state":
            return CutterConfig.ENABLE_UNBREAKING;
        case "enchantment_fortune_state":
            return CutterConfig.ENABLE_LUCK;
        case "enchantment_fortune_multiplier":
            return CutterConfig.LUCK_MULTIPLIER;
        }
        return null;
    }

    public Object getWithPlayer(final Player player, final String identifier) {
        switch (identifier) {
        case "player_active":
            return PlayerState.isActive(player);
        case "player_toggled":
            return PlayerState.isToggled(player);
        case "player_world":
            return PlayerState.isWorldEnabled(player);
        case "player_permitted":
            return PlayerState.isPermitted(player);
        case "player_break_limit":
            return Limiter.getLimit(player);
        }
        return getWithoutPlayer(identifier);
    }

}
