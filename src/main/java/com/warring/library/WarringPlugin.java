package com.warring.library;

import com.warring.library.storage.MapStorage;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class WarringPlugin extends JavaPlugin {

    private static WarringPlugin inst;
    public String noPermission = "&c&l[!] &4You&c do not have permission...";
    @Getter
    private String nmsver;
    @Getter
    boolean useOldMethods = false;

    public WarringPlugin() {
        inst = this;
        nmsver = ServerVersion.getVersion().name();
        if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.startsWith("v1_7_")) {
            useOldMethods = true;
        }
        MapStorage<Integer, Integer> mapStorage = new MapStorage<>();
        mapStorage.put(1, 2);
        mapStorage.get(1);
    }

    public void registerListeners(Listener... listeners) {
        for (Listener list : listeners) {
            Bukkit.getPluginManager().registerEvents(list, this);
        }
    }

    public static WarringPlugin getInstance() {
        return inst;
    }

    public void setNoPermission(String noPermission) {
        this.noPermission = noPermission;
    }

    public static void setInstance(WarringPlugin inst) {
        WarringPlugin.inst = inst;
    }

}
