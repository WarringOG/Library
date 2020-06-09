package com.warring.library.commands;

import com.warring.library.WarringPlugin;
import com.warring.library.utils.CommandMapUtils;
import com.warring.library.utils.Utils;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExe implements CommandExecutor {

    @Getter
    private CommandStart start;
    private ICommand cmd;

    public CommandExe(CommandStart start, ICommand cmd) {
        this.start = start;
        this.cmd = cmd;
    }

    public void register() {
        CommandMapUtils.registerCommand(WarringPlugin.getInstance(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (String aliases : start.getAliases()) {
            if (aliases.equalsIgnoreCase(command.getName()) || command.getName().equalsIgnoreCase(start.getName())) {
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
        }
        return false;
    }
}
