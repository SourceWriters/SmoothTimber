package com.syntaxphoenix.spigot.smoothtimber.compatibility.jobsreborn;

import java.util.EnumMap;
import java.util.HashMap;

import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddonConfig;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.WoodType;

public final class JobsRebornConfig extends CompatibilityAddonConfig<JobsReborn> {

    public static final EnumMap<WoodType, double[]> DEFAULT_WOOD_DATA = new EnumMap<>(WoodType.class);
    public static HashMap<String, EnumMap<WoodType, double[]>> JOB_DATA = new HashMap<>();

    /*
     * 
     */

    protected JobsRebornConfig(JobsReborn addon, PluginPackage pluginPackage) {
        super(addon, pluginPackage, JobsRebornMigration.class, 1);
    }

    @Override
    protected void onSetup() {
        VersionChanger change = PluginUtils.CHANGER;
        for (WoodType type : WoodType.values()) {
            if (change.isSupported(type)) {
                DEFAULT_WOOD_DATA.put(type, new double[] {
                    1.0D,
                    1.0D,
                    1.0D
                });
            }
        }
        checkExperience("default", DEFAULT_WOOD_DATA);
        checkExperience("jobs.example", DEFAULT_WOOD_DATA);
    }

    @Override
    protected void onLoad() {
        JOB_DATA.clear();

        String[] keys = getKeys("jobs");
        if (keys.length == 0) {
            return;
        }

        for (String key : keys) {
            EnumMap<WoodType, double[]> woodData = JOB_DATA.computeIfAbsent(key, ignore -> DEFAULT_WOOD_DATA.clone());
            checkExperience("jobs." + key, woodData);
            JOB_DATA.put(key, woodData);
        }

    }

    @Override
    protected void onUnload() {
        DEFAULT_WOOD_DATA.clear();
        JOB_DATA.clear();
    }

    /*
     * 
     */

    private void checkExperience(String path, EnumMap<WoodType, double[]> experience) {
        for (WoodType type : WoodType.values()) {
            String key = path + '.' + type.name().toLowerCase();
            double[] defaultData = DEFAULT_WOOD_DATA.get(type);
            double[] value = new double[3];
            System.arraycopy(defaultData, 0, value, 0, 3);
            value[0] = check(key + ".income", value[0]);
            value[1] = check(key + ".points", value[1]);
            value[2] = check(key + ".experience", value[2]);
            experience.put(type, value);
        }
    }

}
