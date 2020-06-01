package com.warring.library.utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static String toColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static int getTotalExperience(Player player) {
        int ver = Integer.parseInt(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].split("_")[1]);
        int efl = (ver >= 8) ? getLevelExpNew(player.getLevel()) : getLevelExpOld(player.getLevel());
        int exp = Math.round(efl * player.getExp());
        for (int currentLevel = player.getLevel(); currentLevel > 0; --currentLevel, exp += getLevelExpNew(currentLevel)) {}
        if (exp < 0) {
            exp = 0;
        }
        return exp;
    }

    public static int getLevelExpOld(int level) {
        return (level >= 30) ? (62 + (level - 30) * 7) : ((level >= 15) ? (17 + (level - 15) * 3) : 17);
    }

    public static int getLevelExpNew(int level) {
        return (level < 16) ? (level * 2 + 7) : ((level < 31) ? (level * 5 - 38) : (level * 9 - 158));
    }

    public static String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor, ChatColor incompleteColor) {
        if (max == 0) {
            return completedColor + StringUtils.repeat(Character.toString(symbol), totalBars);
        }
        double percent = getPercentageDouble(current, max) / 100;
         int completeBars = (int)(totalBars * percent);
        return completedColor + StringUtils.repeat(Character.toString(symbol), completeBars) + incompleteColor + StringUtils.repeat(Character.toString(symbol), totalBars - completeBars);
    }


    public static int getRandomInteger(int lower, int upper) {
        Random random = new Random();
        return random.nextInt(upper - lower + 1) + lower;
    }


    public static int getPercentage(int current, int max) {
        if (max == 0) {
            return 100;
        }
        return (Math.round(current * 100/ max * 100) / 100);
    }

    public static double getPercentageDouble(int current, int max) {
        if (max == 0) {
            return 100;
        }
        return ((current * 100/ max * 100) / 100);
    }

    public static String format(long seconds) {
        if (seconds == 0L) {
            return "0s";
        }
        long minute = seconds / 60L;
        seconds %= 60L;
        long hour = minute / 60L;
        minute %= 60L;
        final long day = hour / 24L;
        hour %= 24L;
        final StringBuilder time = new StringBuilder();
        if (day != 0L) {
            time.append(day).append("d ");
        }
        if (hour != 0L) {
            time.append(hour).append("h ");
        }
        if (minute != 0L) {
            time.append(minute).append("m ");
        }
        if (seconds != 0L) {
            time.append(seconds).append("s");
        }
        return time.toString().trim();
    }

    public static Location getLocFromConfig(String location) {
        String[] args = location.split(";");
        Location loc = new Location(Bukkit.getWorld(args[3]), Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
        return loc;
    }

    public static String getLocationToConfig(Location loc) {
        String formatLoc = loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getWorld().getName();
        return formatLoc;
    }
}
