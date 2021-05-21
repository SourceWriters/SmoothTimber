package com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CooldownTimer extends Thread {

    private boolean alive = true;
    private boolean running = false;

    private final List<Cooldown> active = Collections.synchronizedList(new ArrayList<>());

    public CooldownTimer() {
        setDaemon(true);
        start();
    }

    void add(Cooldown cooldown) {
        active.add(cooldown);
    }

    @Override
    public void run() {
        while (alive) {
            while (running) {
                for (Cooldown cooldown : active) {
                    if (!cooldown.isTriggerable()) {
                        cooldown.decrement();
                    }
                }
                try {
                    Thread.sleep(1);
                } catch (Throwable e) {
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // Idk, just ignore
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
    }

    public boolean isAvailable() {
        return alive;
    }

}