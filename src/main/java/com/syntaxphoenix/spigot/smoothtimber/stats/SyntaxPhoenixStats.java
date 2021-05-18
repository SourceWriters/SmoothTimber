package com.syntaxphoenix.spigot.smoothtimber.stats;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SyntaxPhoenixStats {

    public static final double SYNTAXPHOENIX_STATS_VERSION = 0.4;

    private static final String URL = "http://stats.syntaxphoenix.com/submit/?type=bukkit";

    private final File file = new File("plugins/SyntaxPhoenixStats", "config.yml");
    private FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    private final JavaPlugin plugin;
    private final String service_id;

    private static String serverUUID;
    private boolean logging = false;

    public SyntaxPhoenixStats(String service_id, JavaPlugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin can not be null!");
        }
        this.plugin = plugin;
        this.service_id = service_id;
        config.addDefault("enabled", true);
        config.addDefault("ServerUUID", UUID.randomUUID().toString());
        config.addDefault("Logging", false);
        config.options()
            .header("SyntaxPhoenixStats collects some data for SyntaxPhoenix to give you an better experience. \n"
                + "This software has nearly no effect on the server performance!\n"
                + "For more Infos check out https://stats.syntaxphoenix.com")
            .copyDefaults(true);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverUUID = config.getString("ServerUUID");
        logging = config.getBoolean("Logging");

        if (config.getBoolean("enabled")) {
            boolean found = false;

            for (Class<?> service : Bukkit.getServicesManager().getKnownServices()) {
                try {
                    service.getField("SYNTAXPHOENIX_STATS_VERSION");
                    found = true;
                    break;
                } catch (NoSuchFieldException ignored) {
                }
            }
            Bukkit.getServicesManager().register(SyntaxPhoenixStats.class, this, plugin, ServicePriority.Normal);
            if (!found) {
                DataSubmitter();
            }
        }
    }

    private void DataSubmitter() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!plugin.isEnabled()) {
                    timer.cancel();
                    return;
                }

                Bukkit.getScheduler().runTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        submitData();
                    }
                });
            }
        }, 1000 * 60 * 5, 1000 * 60 * 15);
    }

    public JsonObject collectPluginData() {
        JsonObject data = new JsonObject();

        String name = plugin.getDescription().getName();
        String version = plugin.getDescription().getVersion();

        data.addProperty("Name", name);
        data.addProperty("Service-ID", this.service_id);
        data.addProperty("Version", version);

        return data;
    }

    public boolean isConnectedToBungee() {
        boolean bungee = Bukkit.getServer().spigot().getConfig().getBoolean("settings.bungeecord");
        boolean onlineMode = Bukkit.getServer().getOnlineMode();
        if (bungee && (!(onlineMode))) {
            return true;
        }
        return false;
    }

    public String getServerSoftware() {
        String server_software;
        String version = plugin.getServer().getVersion();
        if (version.contains("Bukkit")) {
            server_software = "Craftbukkit";
        } else if (version.contains("Spigot")) {
            server_software = "Spigot";
        } else if (version.contains("PaperSpigot")) {
            server_software = "PaperSpigot";
        } else {
            server_software = version;
        }
        return server_software;
    }

    public JsonObject collectServerData() {
        JsonObject data = new JsonObject();

        int playerAmount = Bukkit.getOnlinePlayers().size();
        boolean onlineMode = Bukkit.getOnlineMode();
        boolean bungeecord = isConnectedToBungee();
        String bukkitVersion = Bukkit.getVersion();
        String server_software = getServerSoftware();
        bukkitVersion = bukkitVersion.substring(bukkitVersion.indexOf("MC: ") + 4, bukkitVersion.length() - 1);

        String javaVersion = System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        String osVersion = System.getProperty("os.version");
        int coreCount = Runtime.getRuntime().availableProcessors();
        long memory = Runtime.getRuntime().totalMemory();

        data.addProperty("ServerUUID", SyntaxPhoenixStats.serverUUID);

        data.addProperty("BukkitVersion", bukkitVersion);
        data.addProperty("PlayerAmount", playerAmount);
        data.addProperty("onlineMode", onlineMode);
        data.addProperty("BungeeCord", bungeecord);
        data.addProperty("ServerSoftware", server_software);

        data.addProperty("JavaVersion", javaVersion);
        data.addProperty("OS", osName);
        data.addProperty("OS-Version", osVersion);
        data.addProperty("OS-Arch", osArch);
        data.addProperty("Core-Count", coreCount);
        data.addProperty("Memory", memory);

        return data;
    }

    private void submitData() {
        JsonObject data = collectServerData();

        JsonArray pluginData = new JsonArray();
        for (Class<?> service : Bukkit.getServicesManager().getKnownServices()) {
            try {
                service.getField("SYNTAXPHOENIX_STATS_VERSION"); // Our identifier :)
            } catch (NoSuchFieldException ignored) {
                continue; // Continue "searching"
            }
            // Found one!
            try {
                pluginData.add((JsonObject) service.getMethod("collectPluginData").invoke(Bukkit.getServicesManager().load(service)));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {

            }
            data.add("plugins", pluginData);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendDataToServer(data);
                    } catch (Exception e) {
                        if (logging) {
                            plugin.getLogger().log(Level.WARNING, "Could not submit plugin stats of " + plugin.getName(), e);
                        }
                    }
                }
            }).start();
        }
    }

    private void sendDataToServer(JsonObject data) throws Exception {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        if (Bukkit.isPrimaryThread()) {
            throw new IllegalAccessException("This method must not be called from the main thread!");
        }

        HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "SyntaxPhoenixStats-ServerVersion/" + SYNTAXPHOENIX_STATS_VERSION);

        connection.setDoOutput(true);
        PrintStream ps = new PrintStream(connection.getOutputStream());
        ps.print("data=" + data.toString());
        connection.getInputStream();

        ps.close();
        connection.getInputStream().close();
    }

}
