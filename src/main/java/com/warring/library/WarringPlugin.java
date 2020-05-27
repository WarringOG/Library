package com.warring.library;

import com.warring.library.events.EventStart;
import com.warring.library.events.backend.EventInfo;
import com.warring.library.events.backend.EventManager;
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

    public WarringPlugin() {
        inst = this;
        new EventManager(BlockBreakEvent.class).register(event -> {
            event.g
        });
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
