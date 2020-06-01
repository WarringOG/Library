package com.warring.library.files;

import lombok.Getter;

import java.util.List;

@Getter
public class YMLFileManager {

    private YmlCreator creator;

    public YMLFileManager(String name) {
        creator = new YmlCreator(name);
    }

    public void setString(String location, String set) {
        creator.getYmlFile().set(location, set);
        creator.saveResource();
        creator.reloadResource();
    }

    public void setInt(String location, int set) {
        setString(location, set + "");
    }

    public void setDouble(String locatio, double set) {
        setString(locatio, set +"");
    }

    public void setStringList(String location, List<String> set) {
        creator.getYmlFile().set(location, set);
        creator.saveResource();
        creator.reloadResource();
    }

    public void setIntegerList(String location, List<Integer> set) {
        creator.getYmlFile().set(location, set);
        creator.saveResource();
        creator.reloadResource();
    }

    public String getString(String location) {
        return creator.getYmlFile().getString(location);
    }

    public int getInteger(String location) {
        return creator.getYmlFile().getInt(location);
    }

    public double getDouble(String location) {
        return creator.getYmlFile().getDouble(location);
    }

    public List<String> getStringList(String location) {
        return creator.getYmlFile().getStringList(location);
    }

    public List<Integer> getIntegerList(String location) {
        return creator.getYmlFile().getIntegerList(location);
    }

    public void save() {
        creator.saveResource();
    }

    public void reload() {
        creator.reloadResource();
    }

}
