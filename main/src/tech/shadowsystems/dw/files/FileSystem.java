package tech.shadowsystems.dw.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import tech.shadowsystems.dw.stats.SystemStats;

import java.io.File;
import java.io.IOException;

/**
 * Copyright © 2016 Jordan Osterberg and Shadow Technical Systems LLC. All rights reserved. Please email jordan.osterberg@shadowsystems.tech for usage rights and other information.
 */
public class FileSystem {
    private static FileSystem ourInstance = new FileSystem();
    public static FileSystem getInstance() {
        return ourInstance;
    }
    private FileSystem() {
    }

    private FileConfiguration dataFileConfig;
    private File dataFile;

    private SystemStats systemStats;

    public void onEnable(Plugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        this.dataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!this.dataFile.exists()) {
            try {
                 this.dataFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        this.dataFileConfig = YamlConfiguration.loadConfiguration(this.dataFile);

        // Default loading
        if (getDataFileConfig().get("stats.gameid") == null) {
            getDataFileConfig().set("stats.gameid", "1");
        }

        saveData();

        this.systemStats = new SystemStats(Integer.parseInt(getDataFileConfig().getString("stats.gameid")));
    }

    public void onDisable() {
        getDataFileConfig().set("stats.gameid", "" + getSystemStats().getGameid());

        saveData();
    }

    public FileConfiguration getDataFileConfig() {
        return dataFileConfig;
    }

    public void setDataFileConfig(FileConfiguration dataFileConfig) {
        this.dataFileConfig = dataFileConfig;
    }

    public void saveData() {
        try {
            this.dataFileConfig.save(this.dataFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public SystemStats getSystemStats() {
        return systemStats;
    }
}
