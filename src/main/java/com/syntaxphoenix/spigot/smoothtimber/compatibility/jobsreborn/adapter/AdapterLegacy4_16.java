package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.adapter;

import static java.lang.invoke.MethodType.*;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.economy.BufferedEconomy;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.IncompatiblePluginException;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.JobAdapter;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.adapter.handle.LookHandle;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Container;

public class AdapterLegacy4_16 extends JobAdapter {


    private final Container<BufferedEconomy> economy = Container.of();
    private final LookHandle nameHandle;
    
    public AdapterLegacy4_16() {
        Lookup lookup = MethodHandles.publicLookup();
        try {
            nameHandle = new LookHandle(lookup.findVirtual(Job.class, "getJobKeyName", methodType(String.class)));
        } catch (Exception exp) {
            throw new IncompatiblePluginException("Can't find all methods needed!");
        }
    }
    
    @Override
    public BufferedEconomy getEconomy() {
        if(economy.isPresent()) {
            return economy.get();
        }
        return economy.replace(Jobs.getEconomy()).get();
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
    public void close() {
        economy.replace(null);
    }

}
