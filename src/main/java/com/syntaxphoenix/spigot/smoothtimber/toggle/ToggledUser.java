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

    public ToggledUser(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.time = -1;
    }

    public ToggledUser(UUID uniqueId, int time) {
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

    protected void onToggle(boolean status) {
        Player player = Bukkit.getPlayer(uniqueId);
        if (player == null) {
            return;
        }

        boolean enable = CutterConfig.TOGGLEABLE.test(() -> false);
        boolean timed = time != -1;

        Message message = getMessage(enable, timed, status);

        player.sendMessage(Message.GLOBAL_PREFIX.colored() + ' ' + message.colored(getArguments(timed, time)));
    }

    public String[][] getArguments(boolean timed, int amount) {
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
        } else {
            return new String[][] {
                {
                    "%tool%",
                    Message.TOOLS_WOODCHOPPER.message()
                }
            };
        }
    }

    public Message getMessage(boolean trigger, boolean timed, boolean state) {
        if (trigger) {
            if (state) {
                return timed ? Message.TOGGLE_ON_TIMED : Message.TOGGLE_ON_FOREVER;
            } else {
                return Message.TOGGLE_OFF_FOREVER;
            }
        } else {
            if (state) {
                return timed ? Message.TOGGLE_OFF_TIMED : Message.TOGGLE_OFF_FOREVER;
            } else {
                return Message.TOGGLE_ON_FOREVER;
            }
        }
    }

}
