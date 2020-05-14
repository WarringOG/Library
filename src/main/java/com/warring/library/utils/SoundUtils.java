package com.warring.library.utils;

import com.warring.library.WarringPlugin;
import org.bukkit.entity.Player;

public class SoundUtils {

    public static void playSound(Player p, String path) {
        if (WarringPlugin.getInstance().getConfig().getBoolean("Sounds." + String.valueOf(path) + ".Enabled")) {
            float volume = (float) WarringPlugin.getInstance().getConfig().getDouble("Sounds." + String.valueOf(path) + ".Volume");
            float pitch = (float) WarringPlugin.getInstance().getConfig().getDouble("Sounds." + String.valueOf(path) + ".Pitch");
            p.playSound(p.getLocation(), Sounds.valueOf(WarringPlugin.getInstance().getConfig().getString("Sounds." + String.valueOf(path) + ".Name").toUpperCase()).bukkitSound(), volume, pitch);
        }
    }

}
