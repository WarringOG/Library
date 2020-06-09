package com.warring.library.commands;

import com.google.common.collect.Lists;

import java.util.List;

public class CommandStart {

    private String name;
    private String permission = null;
    private boolean playerOnly = false;
    private List<String> aliases;

    private CommandStart(String name) {
        this.name = name;
        aliases = Lists.newArrayList();
        aliases.add(name);
    }

    public static CommandStart start(String name) {
        return new CommandStart(name);
    }

    public CommandStart setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public CommandStart playerOnly(boolean playerOnly) {
        this.playerOnly = playerOnly;
        return this;
    }

    public void addAliases(String... aliases) {
        for (String s : aliases) {
            this.aliases.add(s);
        }
    }

    public List<String> getAliases() {
        return aliases;
    }

    public CommandExe doCommand(ICommand command) {
        return new CommandExe(this, command);
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }
}
