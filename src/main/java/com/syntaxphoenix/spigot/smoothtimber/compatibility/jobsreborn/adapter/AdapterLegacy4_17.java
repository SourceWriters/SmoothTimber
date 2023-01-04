package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.adapter;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.economy.BufferedEconomy;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn.JobAdapter;
import com.syntaxphoenix.spigot.smoothtimber.utilities.Container;

public class AdapterLegacy4_17 extends JobAdapter {

    private final Container<BufferedEconomy> economy = Container.of();

    @Override
    public BufferedEconomy getEconomy() {
        if (economy.isPresent()) {
            return economy.get();
        }
        return economy.replace(Jobs.getEconomy()).get();
    }

    @Override
    public String getName(final Job job) {
        return job.getName();
    }

    @Override
    public void addExperience(final JobProgression progression, final double value) {
        progression.addExperience(value);
    }

    @Override
    public void close() {
        economy.replace(null);
    }

}
