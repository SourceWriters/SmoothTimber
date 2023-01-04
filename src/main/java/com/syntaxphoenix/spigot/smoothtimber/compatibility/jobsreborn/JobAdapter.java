package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn;

import java.util.HashMap;

import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.gamingmesh.jobs.economy.BufferedEconomy;

public abstract class JobAdapter {

    public abstract BufferedEconomy getEconomy();

    public abstract String getName(Job job);

    public abstract void addExperience(JobProgression progression, double value);

    public void addPointsAndMoney(final JobsPlayer player, final double pointValue, final double moneyValue) {
        final BufferedEconomy economy = getEconomy();
        if (economy == null || pointValue + moneyValue <= 0) {
            return;
        }
        final HashMap<CurrencyType, Double> map = new HashMap<>();
        if (pointValue > 0) {
            map.put(CurrencyType.POINTS, pointValue);
        }
        if (moneyValue > 0) {
            map.put(CurrencyType.MONEY, pointValue);
        }
        economy.pay(player, map);
    }

    public void close() {}

}
