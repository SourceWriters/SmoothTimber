package com.syntaxphoenix.spigot.smoothtimber.toggle;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;

public class ToggledUser {

    private final UUID uniqueId;
    private int time;

    /*
     * 
     */

    public ToggledUser(final UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.time = -1;
    }

    public ToggledUser(final UUID uniqueId, final int time) {
        this.uniqueId = uniqueId;
        this.time = time;
    }

    /*
     * 
     */

    public final UUID getUniqueId() {
        return uniqueId;
    }

    public int getRemainingTime() {
        return time;
    }

    /*
     * 
     */

    protected boolean update() {
        if (time == -1) {
            return false;
        }
        if ((time -= 1) == 0) {
            return true;
        }
        return false;
    }

    /*
     * 
     */

    protected void onToggle(final boolean status) {
        final Player player = Bukkit.getPlayer(uniqueId);
        if (player == null) {
            return;
        }

        final boolean enable = CutterConfig.TOGGLEABLE.test(() -> false);
        final boolean timed = time != -1;

        final Message message = getMessage(enable, timed, status);

        player.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + message.colored(getArguments(timed, time)));
    }

    public String[][] getArguments(final boolean timed, final int amount) {
        if (timed) {
            return new String[][] {
                {
                    "%tool%",
                    Message.TOOLS_WOODCHOPPER.message()
                },
                {
                    "%time%",
                    amount + " " + (amount == 1 ? Message.TIME_SECOND.message() : Message.TIME_SECONDS.message())
                }
            };
        }
        return new String[][] {
            {
                "%tool%",
                Message.TOOLS_WOODCHOPPER.message()
            }
        };
    }

    public Message getMessage(final boolean trigger, final boolean timed, final boolean state) {
        if (trigger) {
            if (state) {
                return timed ? Message.TOGGLE_ON_TIMED : Message.TOGGLE_ON_FOREVER;
            }
            return Message.TOGGLE_OFF_FOREVER;
        }
        if (state) {
            return timed ? Message.TOGGLE_OFF_TIMED : Message.TOGGLE_OFF_FOREVER;
        }
        return Message.TOGGLE_ON_FOREVER;
    }

}
