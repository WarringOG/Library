package com.warring.library.utils;

import com.warring.library.WarringPlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YmlCreator {

    private String ymlName;
    private File file;
    private FileConfiguration ymlFile;


    public YmlCreator(String ymlFileName) {
        this.ymlName = ymlFileName;
        file = new File(WarringPlugin.getInstance().getDataFolder(), ymlName + ".yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            WarringPlugin.getInstance().saveResource(ymlName + ".yml", false);
        }
        ymlFile = new YamlConfiguration();
        try {
            ymlFile.load(file);
        } catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public YmlCreator(File folder, String ymlFileName) {
        this.ymlName = ymlFileName;
        file = new File(folder, ymlName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            WarringPlugin.getInstance().saveResource(ymlName, false);
        }
        ymlFile = new YamlConfiguration();
        try {
            ymlFile.load(file);
        } catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public void saveResource() {
        if(ymlFile == null || file == null) return;
        try {
            ymlFile.save(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void reloadResource() {
        try {
            ymlFile.load(file);
        } catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
        YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getYmlFile() {
        return ymlFile;
    }
}
