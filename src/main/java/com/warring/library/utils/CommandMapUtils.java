package com.warring.library.utils;

import com.warring.library.WarringPlugin;
import com.warring.library.commands.CommandExe;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class CommandMapUtils {

    public static void unregisterCommand(String s) {
        getCommandMap().getCommand(s).unregister(getCommandMap());
    }

    public static void unregisterCommands(Plugin plugin) {
        if (getCommands().isEmpty()) return;
        getCommands().stream().filter(command -> WarringPlugin.getInstance().getServer().getPluginCommand(command.getName()) != null).filter(command -> {
            return WarringPlugin.getInstance().getServer().getPluginCommand(command.getName()).getPlugin().getName().equals(plugin.getName());
        }).forEach(command -> {
            command.unregister(getCommandMap());
        });
    }

    public static void registerCommand(Plugin plugin, CommandExe exe) {
        getCommandMap().register(plugin.getDescription().getName(), getPluginCommand(plugin, exe));
    }

    public static Collection<Command> getCommands() {
        try {
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            SimpleCommandMap cmdm = (SimpleCommandMap) field.get(WarringPlugin.getInstance().getServer().getPluginManager());
            return cmdm.getCommands();
        }
        catch (IllegalAccessException | NoSuchFieldException ex2) {
            throw new RuntimeException("Unable to get the Bukkit KnowmCommand Map.", ex2);
        }
    }

    private static CommandMap getCommandMap() {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            return (CommandMap)field.get(WarringPlugin.getInstance().getServer());
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex2) {
            throw new RuntimeException("Unable to get the Bukkit CommandMap.", ex2);
        }
    }

    private static PluginCommand getPluginCommand(Plugin plugin, CommandExe exe) {
        try {
            Constructor<PluginCommand> commandConstructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            commandConstructor.setAccessible(true);
            PluginCommand pluginCommand = commandConstructor.newInstance(exe.getStart().getAliases().get(0), plugin);
            if (!exe.getStart().getAliases().isEmpty())
                pluginCommand.setAliases(exe.getStart().getAliases());
            pluginCommand.setExecutor(exe);
            return pluginCommand;
        }
        catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException ex2) {
            throw new RuntimeException("Unable to get PluginCommand.", ex2);
        }
    }
}
