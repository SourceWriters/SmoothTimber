package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn;

import java.util.EnumMap;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerTreeFallEvent;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.WoodType;

public final class JobsRebornFallListener implements Listener {

    public final JobAdapter adapter;

    public JobsRebornFallListener(JobAdapter adapter) {
        this.adapter = adapter;
    }

    @EventHandler(ignoreCancelled = true)
    public void onFallEvent(AsyncPlayerTreeFallEvent event) {
        WoodType[] types = event.getTypes();
        JobsPlayer player = Jobs.getPlayerManager().getJobsPlayer(event.getPlayer());
        double pointValue = 0;
        double moneyValue = 0;
        List<JobProgression> progressions = player.getJobProgression();
        for (JobProgression progression : progressions) {
            EnumMap<WoodType, double[]> data = JobsRebornConfig.JOB_DATA.get(adapter.getName(progression.getJob()));
            if (data == null) {
                continue;
            }
            double expValue = 0;
            for (WoodType type : types) {
                double[] values = data.get(type);
                int amount = event.getAmount(type);
                moneyValue += (values[0] * amount);
                pointValue += (values[1] * amount);
                expValue += (values[2] * amount);
            }
            adapter.addExperience(progression, expValue);
        }
        adapter.addPointsAndMoney(player, pointValue, moneyValue);
    }

    void close() {
        adapter.close();
    }

}