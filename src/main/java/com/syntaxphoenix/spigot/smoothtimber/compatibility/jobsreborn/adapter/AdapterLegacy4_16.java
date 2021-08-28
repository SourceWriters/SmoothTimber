package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.adapter;

import static java.lang.invoke.MethodType.*;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;

import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.gamingmesh.jobs.economy.PaymentData;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.IncompatiblePluginException;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.JobAdapter;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.adapter.handle.LookHandle;

public class AdapterLegacy4_16 extends JobAdapter {

    private final LookHandle nameHandle;
    private final LookHandle moneyHandle;
    
    public AdapterLegacy4_16() {
        Lookup lookup = MethodHandles.publicLookup();
        try {
            nameHandle = new LookHandle(lookup.findVirtual(Job.class, "getJobKeyName", methodType(String.class)));
            moneyHandle = new LookHandle(lookup.findVirtual(PaymentData.class, "addAmount", methodType(Void.class).appendParameterTypes(CurrencyType.class, Double.class)));
        } catch (Exception exp) {
            throw new IncompatiblePluginException("Can't find all methods needed!");
        }
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
        player.getPointsData().addPoints(pointValue);
        moneyHandle.invokeWithArguments(player.getPaymentLimit(), CurrencyType.MONEY, moneyValue);
    }

}
