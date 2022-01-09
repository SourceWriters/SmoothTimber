package com.syntaxphoenix.spigot.smoothtimber.config.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import com.syntaxphoenix.spigot.smoothtimber.config.ActionType;
import com.syntaxphoenix.spigot.smoothtimber.config.Message;
import com.syntaxphoenix.spigot.smoothtimber.config.STConfig;
import com.syntaxphoenix.spigot.smoothtimber.config.migration.CutterMigration;
import com.syntaxphoenix.spigot.smoothtimber.utilities.PluginUtils;
import com.syntaxphoenix.spigot.smoothtimber.utilities.cooldown.CooldownHelper;
import com.syntaxphoenix.spigot.smoothtimber.utilities.limit.Limiter;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;

import static org.bukkit.Bukkit.getLogger;

public final class CutterConfig extends STConfig {

    public static final CutterConfig INSTANCE = new CutterConfig();

    public static int CHECK_RADIUS = 2;
    public static int ROOT_DEPTH = 3;
    public static List<String> CUTTER_MATERIALS = new ArrayList<>();
    public static List<String> CUTTER_PERMISSIONS = new ArrayList<>();

    public static boolean ENABLE_WOOD_PERMISSIONS = false;
    public static boolean ENABLE_CUTTER_PERMISSIONS = false;

    public static ActionType SNEAK = ActionType.OFF_ACTION;
    public static ActionType TOGGLEABLE = ActionType.OFF_ACTION;
    public static boolean SYNC_BLOCK_DETECTION = false;

    public static boolean ENABLE_BLOCK_LIMIT = true;
    public static int DEFAULT_BLOCK_LIMIT = 1000;
    
    public static boolean ENABLE_COOLDOWN = false;
    public static long DEFAULT_COOLDOWN_TIME = 20000;

    public static boolean ENABLE_UNBREAKING = true;

    public static boolean ENABLE_LUCK = false;
    public static double LUCK_MULTIPLIER = 1.0;

    public static boolean ENABLE_EXCLUSION = false;
    public static List<Material> EXCLUDED_MATERIALS = new ArrayList<>();

    public static boolean ENABLE_INCLUSION = false;
    public static List<Material> INCLUDED_MATERIALS = new ArrayList<>();

    public static boolean ENABLE_WORLD = false;
    public static boolean ENABLE_WORLD_BLACKLIST = false;
    public static List<String> WORLD_LIST;

    public static boolean GLOBAL_DEBUG = false;
    public static int GLOBAL_SYNC_TIME = 50;

    /*
     * 
     */

    private CutterConfig() {
        super(new File("plugins/SmoothTimber", "config.yml"), CutterMigration.class, 8);
    }

    /*
     * Type
     */

    @Override
    protected String getSingleType() {
        return Message.TYPE_SETTING_MAIN.message();
    }

    @Override
    protected String getMultipleType() {
        return Message.TYPE_SETTINGS_MAIN.message();
    }

    /*
     * Handle
     */

    @Override
    protected void onSetup() {
        PluginUtils.CHANGER.setupConfig();
    }

    @Override
    protected void onLoad() {

        GLOBAL_DEBUG = check("global.debug", GLOBAL_DEBUG);
        GLOBAL_SYNC_TIME = check("global.sync-time", GLOBAL_SYNC_TIME);

        CHECK_RADIUS = check("cutter.radius", CHECK_RADIUS);
        ROOT_DEPTH = check("cutter.depth", ROOT_DEPTH);
        CUTTER_MATERIALS = check("cutter.materials", CUTTER_MATERIALS);

        SNEAK = ActionType.check(this, "options.sneak", SNEAK);
        TOGGLEABLE = ActionType.check(this, "options.toggleable", TOGGLEABLE);
        SYNC_BLOCK_DETECTION = check("options.sync-detection", SYNC_BLOCK_DETECTION);

        ENABLE_WOOD_PERMISSIONS = check("options.permission.wood-type", ENABLE_WOOD_PERMISSIONS);
        ENABLE_CUTTER_PERMISSIONS = check("options.permission.cutter-type", ENABLE_CUTTER_PERMISSIONS);

        ENABLE_BLOCK_LIMIT = check("limit.enabled", ENABLE_BLOCK_LIMIT);
        DEFAULT_BLOCK_LIMIT = check("limit.default", DEFAULT_BLOCK_LIMIT);

        ENABLE_COOLDOWN = check("cooldown.enabled", ENABLE_COOLDOWN);
        DEFAULT_COOLDOWN_TIME = check("cooldown.time", DEFAULT_COOLDOWN_TIME);

        ENABLE_UNBREAKING = check("enchantments.unbreaking.enabled", ENABLE_UNBREAKING);

        ENABLE_LUCK = check("enchantments.fortune.enabled", ENABLE_LUCK);
        LUCK_MULTIPLIER = check("enchantments.fortune.multiplier", LUCK_MULTIPLIER);

        ENABLE_EXCLUSION = check("exclusion.enabled", ENABLE_EXCLUSION);
        EXCLUDED_MATERIALS = check("exclusion.list", EXCLUDED_MATERIALS);

        ENABLE_INCLUSION = check("inclusion.enabled", ENABLE_INCLUSION);
        List<Material> includedMaterials = check("inclusion.list", INCLUDED_MATERIALS);

        // Materials have to be solid
        INCLUDED_MATERIALS.clear();
        for (Material includedMaterial : includedMaterials) {
            if (includedMaterial.isSolid()) {
                INCLUDED_MATERIALS.add(includedMaterial);
            }else{
                getLogger().info("Ignoring invalid material '"+includedMaterial+"' in the inclusion.list");
            }
        }

        ENABLE_WORLD = check("worlds.enabled", ENABLE_WORLD);
        ENABLE_WORLD_BLACKLIST = check("worlds.blacklist", ENABLE_WORLD_BLACKLIST);
        WORLD_LIST = check("worlds.list", worldStringList());

        //
        // Corrections

        if (CHECK_RADIUS <= 0) {
            set("cutter.radius", CHECK_RADIUS = Math.abs(CHECK_RADIUS));
        }
        if (ROOT_DEPTH < 0) {
            set("cutter.depth", ROOT_DEPTH = Math.abs(ROOT_DEPTH));
        }
        if(DEFAULT_COOLDOWN_TIME <= 0) {
            set("cooldown.enabled", ENABLE_COOLDOWN = false);
            set("cooldown.time", DEFAULT_COOLDOWN_TIME = 20000);
        }

        //
        // Applying

        Locator.setSyncBlockDetection(SYNC_BLOCK_DETECTION);
        Limiter.setEnabled(ENABLE_BLOCK_LIMIT);
        
        CooldownHelper.setEnabled(ENABLE_COOLDOWN);

    }

    @Override
    protected void onUnload() {

    }

    /*
     * 
     */

    @SuppressWarnings("unchecked")
    private List<Material> check(String path, List<Material> input) {
        if (config.contains(path)) {
            List<String> list = (List<String>) get(path);
            ArrayList<Material> output = new ArrayList<>();
            for (String value : list) {
                output.add(Material.valueOf(value.toUpperCase()));
            }
            return output;
        } else {
            ArrayList<String> write = new ArrayList<>();
            if (!input.isEmpty()) {
                for (Material material : input) {
                    write.add(material.name());
                }
            }
            config.set(path, write);
            return input;
        }
    }

    private static List<String> worldStringList() {

        if (WORLD_LIST != null) {
            return WORLD_LIST;
        }

        WORLD_LIST = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            WORLD_LIST.add(world.getName());
        }
        return WORLD_LIST;
    }

}
