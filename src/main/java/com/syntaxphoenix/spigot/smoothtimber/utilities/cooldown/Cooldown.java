package com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown;

public class Cooldown {

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
        return threshhold;
    }

    public boolean isTriggerable() {
        return threshhold <= 0;
    }

    public void trigger() {
        threshhold = cooldown;
    }

    void decrement() {
        threshhold--;
    }

    void reset() {
        threshhold = 0;
    }

}
