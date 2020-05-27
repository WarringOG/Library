package com.warring.library.commands.backend;

import com.google.common.collect.Lists;
import com.warring.library.WarringPlugin;
import com.warring.library.commands.CommandStart;
import com.warring.library.commands.ICommand;
import com.warring.library.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class CommandManager<T> {

    private String name;
    private String perm;
    private List<CommandPost> infos;
    private boolean playerOnly;

    public CommandManager(String name, String permission) {
        this.name = name;
        this.perm = permission;
        infos = Lists.newArrayList();
        playerOnly = false;
    }

    public CommandManager(String name) {
        this.name = name;
        infos = Lists.newArrayList();
        playerOnly = false;
    }

    public CommandManager(String name, boolean playerOnly) {
        this.name = name;
        this.playerOnly = playerOnly;
        infos = Lists.newArrayList();
    }

    public CommandManager(String name, String perm, boolean playerOnly) {
        this.name = name;
        this.playerOnly = playerOnly;
        infos = Lists.newArrayList();
        this.perm = perm;
    }

    public void addArgs(CommandPost... posts) {
        for (CommandPost i : posts) {
            infos.add(i);
        }
    }

    public abstract void base(T object);

    public void register() {
        CommandStart.start(name).playerOnly(playerOnly).setPermission(perm == null ? null : perm).doCommand(new ICommand() {
            @Override
            public void onCommand(CommandSender sender, String[] args) {
                if (args.length == 0) {
                    base((T) sender);
                    return;
                }
                CommandPost post = null;
                CommandInfo inf = null;
                boolean playerOnly = false;
                for (CommandPost cmd : infos) {
                    CommandInfo info = cmd.getClass().getAnnotation(CommandInfo.class);
                    if (info == null) {
                        post = cmd;
                        continue;
                    }
                    for (String a : info.aliases()) {
                        if (a.equalsIgnoreCase(args[0])) {
                            post = cmd;
                            inf = info;
                            playerOnly = info.playerOnly();
                            break;
                        }
                    }
                }
                if (playerOnly && !(sender instanceof Player)) {
                    sender.sendMessage("Console can not execute that command.");
                    return;
                }

                if (post == null) {
                    base((T) sender);
                    return;
                }
                if (inf == null) {
                    post.execute((T)sender, args);
                    return;
                }
                if (!sender.hasPermission(inf.permission())) {
                    sender.sendMessage(Utils.toColor(WarringPlugin.getInstance().noPermission));
                    return;
                }
                String[] newArgs = removeTheElement(args, 0);
                post.execute((T)sender, newArgs);
            }
        }).register();
    }

    private String[] removeTheElement(String[] arr, int index) {

        // If the array is empty
        // or the index is not in array range
        // return the original array
        if (arr == null
                || index < 0
                || index >= arr.length) {

            return arr;
        }

        // Create another array of size one less
        String[] anotherArray = new String[arr.length - 1];

        // Copy the elements except the index
        // from original array to the other array
        for (int i = 0, k = 0; i < arr.length; i++) {

            // if the index is
            // the removal element index
            if (i == index) {
                continue;
            }

            // if the index is not
            // the removal element index
            anotherArray[k++] = arr[i];
        }

        // return the resultant array
        return anotherArray;
    }

}
