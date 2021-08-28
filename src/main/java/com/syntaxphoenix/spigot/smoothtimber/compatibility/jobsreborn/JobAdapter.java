package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn;

import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;

public abstract class JobAdapter {
    
    public abstract String getName(Job job);
    
    public abstract void addExperience(JobProgression progression, double value);
    
    public abstract void addPointsAndMoney(JobsPlayer player, double pointValue, double moneyValue);
    
    public void close() {}

}
