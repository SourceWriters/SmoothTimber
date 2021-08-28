package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.adapter;

import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.JobAdapter;

public class AdapterLegacy4_17 extends JobAdapter {

    @Override
    public String getName(Job job) {
        return job.getName();
    }

    @Override
    public void addExperience(JobProgression progression, double value) {
        progression.addExperience(value);
    }

    @Override
    public void addPointsAndMoney(JobsPlayer player, double pointValue, double moneyValue) {
        player.getPointsData().addPoints(pointValue);
        player.getPaymentLimit().addAmount(CurrencyType.MONEY, moneyValue);
    }

}
