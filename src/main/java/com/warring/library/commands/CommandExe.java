package com.warring.library.commands;

import com.warring.library.WarringPlugin;
import com.warring.library.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExe implements CommandExecutor {

    private CommandStart start;
    private ICommand cmd;

    public CommandExe(CommandStart start, ICommand cmd) {
        this.start = start;
        this.cmd = cmd;
    }

    public void register() {
        WarringPlugin.getInstance().getCommand(start.getName()).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (start.getName().equalsIgnoreCase(command.getName())) {
            if (start.getPermission() != null) {
                if (!sender.hasPermission(start.getPermission())) {
                    sender.sendMessage(Utils.toColor(WarringPlugin.getInstance().noPermission));
                    return true;
                }
            }
            if (start.isPlayerOnly()) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Console can not execute this command.");
                    return true;
                }
                Player p = (Player) sender;
                cmd.onCommand(p, args);
                return true;
            }
            cmd.onCommand(sender, args);
            return true;
        }
        return false;
    }
}
