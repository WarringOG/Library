package com.warring.library.files;

import com.google.gson.internal.LinkedTreeMap;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

@Getter
public class JSONFileManager {

    private JSONCreator creator;

    public JSONFileManager(String name) {
        creator = new JSONCreator(name);
    }

    public void setString(String location, String set) {
        creator.getConfigMap().put(location, set);
    }

    public void setObject(String location, Object set) {
        creator.getConfigMap().put(location, set);
    }

    public void setInt(String location, int set) {
        setString(location, set + "");
    }

    public void setDouble(String locatio, double set) {
        setString(locatio, set +"");
    }

    public void setObjectMap(String location, Map<Object, Object> set) {
        creator.getConfigMap().put(location, set);
    }

    public void setJSONArray(String location, JSONArray set) {
        creator.getConfigMap().put(location, set);
    }

    public void setJSONObject(String location, JSONObject set) {
        creator.getConfigMap().put(location, set);
    }

    public String getString(String location) {
        return (String) creator.getConfigMap().get(location);
    }

    public int getInteger(String location) {
        return (int) creator.getConfigMap().get(location);
    }

    public double getDouble(String location) {
        return (double) creator.getConfigMap().get(location);
    }

    public JSONArray getJSONArray(String location) {
        return (JSONArray) creator.getConfigMap().get(location);
    }


    public boolean containsJSONObject(String location) {
        if (!creator.getConfigMap().containsKey(location)) return false;

        if (!(creator.getConfigMap().get(location) instanceof JSONObject)) return false;

        return true;
    }

    public boolean containsTreeMap(String location) {
        if (!creator.getConfigMap().containsKey(location)) return false;

        if (!(creator.getConfigMap().get(location) instanceof LinkedTreeMap)) return false;

        return true;
    }
    public JSONObject getJSONObject(String location) {
        return (JSONObject) creator.getConfigMap().get(location);
    }

    public LinkedTreeMap getTreeMap(String location) {
        return (LinkedTreeMap) creator.getConfigMap().get(location);
    }

    public void load() {
        creator.load();
    }

    public void unload() {
        creator.unload();
    }
}
