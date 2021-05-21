package com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CooldownTimer extends TimerTask {

    private boolean alive = true;
    private boolean running = false;

    private final List<Cooldown> active = Collections.synchronizedList(new ArrayList<>());

    public CooldownTimer() {
        new Timer("Cooldown", true).scheduleAtFixedRate(this, 0, 1);
    }

    void add(Cooldown cooldown) {
        active.add(cooldown);
    }

    @Override
    public void run() {
        if (!running) {
            return;
        }
        for (Cooldown cooldown : active) {
            if (!cooldown.isTriggerable()) {
                cooldown.decrement();
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public void kill() {
        alive = false;
        cancel();
    }

    public boolean isAvailable() {
        return alive;
    }

}