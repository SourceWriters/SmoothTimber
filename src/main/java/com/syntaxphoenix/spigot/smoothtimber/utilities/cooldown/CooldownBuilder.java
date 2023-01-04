package com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown;

import java.util.HashMap;
import java.util.UUID;

public class CooldownBuilder {

    private final CooldownTimer timer;
    private final HashMap<UUID, Cooldown> cooldowns = new HashMap<>();

    private long cooldown = 200L;

    public CooldownBuilder() {
        this(new CooldownTimer());
    }

    public CooldownBuilder(final CooldownTimer timer) {
        this.timer = timer;
    }

    public void setCooldown(final long cooldown) {
        if (cooldown <= 0) {
            throw new IllegalArgumentException("cooldown has to be higher than 0");
        }
        this.cooldown = cooldown;
        if (!cooldowns.isEmpty()) {
            for (final Cooldown cooldownObj : cooldowns.values()) {
                cooldownObj.setCooldown(cooldown);
            }
        }
    }

    public long getCooldown() {
        return cooldown;
    }

    public final CooldownTimer getTimer() {
        return timer;
    }

    public Cooldown get(final UUID uniqueId) {
        return cooldowns.computeIfAbsent(uniqueId, ignore -> {
            final Cooldown cooldownObj = new Cooldown(cooldown);
            timer.add(cooldownObj);
            return cooldownObj;
        });
    }

}
