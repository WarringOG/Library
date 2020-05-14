package com.warring.library.commands;

public class CommandStart {

    private String name;
    private String permission = null;
    private boolean playerOnly = false;

    private CommandStart(String name) {
        this.name = name;
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
