package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.adapter;

import static java.lang.invoke.MethodType.*;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.HashMap;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.gamingmesh.jobs.economy.BufferedEconomy;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.IncompatiblePluginException;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.JobAdapter;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.adapter.handle.LookHandle;

public class AdapterLegacy4_16 extends JobAdapter {

    private final LookHandle nameHandle;
    private final BufferedEconomy economy;
    
    public AdapterLegacy4_16() {
        Lookup lookup = MethodHandles.publicLookup();
        try {
            nameHandle = new LookHandle(lookup.findVirtual(Job.class, "getJobKeyName", methodType(String.class)));
        } catch (Exception exp) {
            throw new IncompatiblePluginException("Can't find all methods needed!");
        }
        this.economy = Jobs.getEconomy();
    }

    @Override
    public String getName(Job job) {
        return (String) nameHandle.invokeWithArguments(job);
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
