package com.syntaxphoenix.spigot.smoothtimber.toggle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;

public class ToggleStorage {

    private final List<ToggledUser> toggled = Collections.synchronizedList(new ArrayList<>());
    private final ToggleTimer timer;

    /*
     * 
     */

    public ToggleStorage(Plugin plugin) {
        this.timer = new ToggleTimer(this, plugin);
    }

    /*
     * 
     */

    public final ToggleTimer getTimer() {
        return timer;
    }

    /*
     * 
     */

    public boolean hasToggled(UUID uniqueId) {
        return getUser(uniqueId).isPresent();
    }

    public Optional<ToggledUser> getUser(UUID uniqueId) {
        return toggled.stream().filter(user -> user.getUniqueId().equals(uniqueId)).findAny();
    }

    public boolean toggle(UUID uniqueId) {
        return toggle(uniqueId, -1);
    }

    public boolean toggle(UUID uniqueId, int time) {
        Optional<ToggledUser> option = getUser(uniqueId);
        if (option.isPresent()) {
            ToggledUser user = option.get();
            user.onToggle(false);
            toggled.remove(user);
            return false;
        } else {
            ToggledUser user = new ToggledUser(uniqueId, time);
            user.onToggle(true);
            toggled.add(user);
            return true;
        }
    }

    /*
     * 
     */

    protected void update() {
        if (CutterConfig.TOGGLEABLE.isDisabled() && !toggled.isEmpty()) {
            toggled.clear();
            return;
        }
        Iterator<ToggledUser> iterator = toggled.stream().filter(user -> user.update()).iterator();
        while (iterator.hasNext()) {
            ToggledUser user = iterator.next();
            user.onToggle(false);
            toggled.remove(user);
        }
    }

}
