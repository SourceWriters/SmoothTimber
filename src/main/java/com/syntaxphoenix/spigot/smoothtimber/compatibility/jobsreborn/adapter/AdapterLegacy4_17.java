package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.adapter;

import java.util.HashMap;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.gamingmesh.jobs.economy.BufferedEconomy;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.JobAdapter;

public class AdapterLegacy4_17 extends JobAdapter {

    private final BufferedEconomy economy;
    
    public AdapterLegacy4_17() {
        this.economy = Jobs.getEconomy();
    }

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
        if((pointValue + moneyValue) <= 0) {
            return;
        }
        HashMap<CurrencyType, Double> map = new HashMap<>();
        if(pointValue > 0) {
            map.put(CurrencyType.POINTS, pointValue);
        }
        if(moneyValue > 0) {
            map.put(CurrencyType.MONEY, pointValue);
        }
        economy.pay(player, map);
    }

}
