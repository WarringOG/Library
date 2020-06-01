package com.warring.library.files;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.warring.library.WarringPlugin;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

@Getter
public class JSONCreator {

    private Gson gson;
    private Map<String, Object> configMap;
    private File file;

    public JSONCreator(String name) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        file = new File(WarringPlugin.getInstance().getDataFolder(), name + ".json");
        if (!file.exists()) {
            WarringPlugin.getInstance().saveResource(name + ".json", false);
        }
    }

    public void load() {
        try {
            configMap = gson.fromJson(new FileReader(file), new HashMap<String, Object>().getClass()) == null ? Maps.newHashMap() : gson.fromJson(new FileReader(file), new HashMap<String, Object>().getClass());
        } catch (FileNotFoundException ignored) {}
    }

    public void unload() {
        String json = gson.toJson(configMap);
        file.delete();
        try {
            Files.write(file.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException ignored) {}
    }
}
