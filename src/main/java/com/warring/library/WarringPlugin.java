package com.warring.library;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.warring.library.commands.CommandExe;
import com.warring.library.commands.backend.CommandManager;
import com.warring.library.events.EventStart;
import com.warring.library.events.Filters;
import com.warring.library.model.Model;
import com.warring.library.utils.CommandMapUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class WarringPlugin extends JavaPlugin {

    private static final List<WarringPlugin> ENABLED_PLUGINS = Lists.newArrayList();
    private Set<CommandExe> commandSet;
    private ImmutableSet<Model> units;
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
    }

    @Override
    public void onEnable() {
        ENABLED_PLUGINS.add(this);
        enable();
    }

    @Override
    public void onDisable() {
        disable();
        if (commandSet != null) {
            CommandMapUtils.unregisterCommands(this);
            commandSet.clear();
        }
        if (units != null) {
            this.units = null;
        }
        ENABLED_PLUGINS.remove(this);
    }

    @Nullable
    public Set<CommandExe> getCommandSet(boolean nullityRegister) {
        return commandSet == null ? nullityRegister ? commandSet = new HashSet<>() : null : commandSet;
    }

    @Nonnull
    public Set<CommandExe> getCommandSet() {
        return getCommandSet(true);
    }

    public void registerCommands(CommandManager... manager) {
        for (CommandManager commandManager : manager) {
            commandManager.register();
            getCommandSet().add(commandManager.getExecutor());
        }
    }

    public abstract void enable();

    public abstract void disable();

    public void registerListeners(Listener... listeners) {
        for (Listener list : listeners) {
            Bukkit.getPluginManager().registerEvents(list, this);
        }
    }

    public void registerModels(Model... units) {
        for (Model unit : units) {
            unit.register();
        }
        this.units = ImmutableSet.copyOf(units);
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

