package com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown;

public class Cooldown {

    private final Object lock = new Object();

    private long cooldown;
    private long threshhold = 0;

    Cooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public long getCooldown() {
        return cooldown;
    }

    public long getTreshhold() {
        synchronized (lock) {
            return threshhold;
        }
    }

    public boolean isTriggerable() {
        synchronized (lock) {
            return threshhold <= 0;
        }
    }

    public void trigger() {
        synchronized (lock) {
            threshhold = cooldown;
        }
    }

    void decrement() {
        synchronized (lock) {
            threshhold -= 1;
        }
    }

    void reset() {
        synchronized (lock) {
            threshhold = 0;
        }
    }

}
