package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn;

import java.util.EnumMap;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.gamingmesh.jobs.economy.PaymentData;
import com.syntaxphoenix.spigot.smoothtimber.event.AsyncPlayerTreeFallEvent;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.WoodType;

public final class JobsRebornFallListenerNew implements Listener {

    protected JobsRebornFallListenerNew() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onFallEvent(AsyncPlayerTreeFallEvent event) {
        WoodType[] types = event.getTypes();
        JobsPlayer player = Jobs.getPlayerManager().getJobsPlayer(event.getPlayer());
        PaymentData payment = player.getPaymentLimit();
        List<JobProgression> progressions = player.getJobProgression();
        for (JobProgression progression : progressions) {
            EnumMap<WoodType, double[]> data = JobsRebornConfig.JOB_DATA.get(progression.getJob().getJobKeyName());
            if (data == null) {
                continue;
            }
            for (WoodType type : types) {
                double[] values = data.get(type);
                int amount = event.getAmount(type);
                player.addPoints(values[1] * amount);
                progression.addExperience(values[2] * amount);
                payment.addAmount(CurrencyType.MONEY, values[0] * amount);
            }
        }
    }

}