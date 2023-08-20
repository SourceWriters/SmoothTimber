package com.syntaxphoenix.spigot.smoothtimber.toggle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.syntaxphoenix.spigot.smoothtimber.config.config.CutterConfig;

public class ToggleStorage {

    private final List<ToggledUser> toggled = Collections.synchronizedList(new ArrayList<>());
    private final ToggleTimer timer;

    /*
     * 
     */

    public ToggleStorage() {
        this.timer = new ToggleTimer(this);
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

    public boolean hasToggled(final UUID uniqueId) {
        return getUser(uniqueId).isPresent();
    }

    public Optional<ToggledUser> getUser(final UUID uniqueId) {
        return toggled.stream().filter(user -> user.getUniqueId().equals(uniqueId)).findAny();
    }

    public boolean toggle(final UUID uniqueId) {
        return toggle(uniqueId, -1);
    }

    public boolean toggle(final UUID uniqueId, final int time) {
        final Optional<ToggledUser> option = getUser(uniqueId);
        if (option.isPresent()) {
            final ToggledUser user = option.get();
            user.onToggle(false);
            toggled.remove(user);
            return false;
        }
        final ToggledUser user = new ToggledUser(uniqueId, time);
        user.onToggle(true);
        toggled.add(user);
        return true;
    }

    /*
     * 
     */

    protected void update() {
        if (CutterConfig.TOGGLEABLE.isDisabled() && !toggled.isEmpty()) {
            toggled.clear();
            return;
        }
        final Iterator<ToggledUser> iterator = toggled.stream().filter(ToggledUser::update).iterator();
        while (iterator.hasNext()) {
            final ToggledUser user = iterator.next();
            user.onToggle(false);
            toggled.remove(user);
        }
    }

}
