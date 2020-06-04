package com.warring.library.utils;

import com.google.common.collect.Lists;
import com.warring.library.enums.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemUtils {

    public static ItemStack getConfigItemNonLegacy(ConfigurationSection sec) {
        String args = sec.getString("Material");
        Material mat = Material.getMaterial(args);
        if (mat == null) {
            mat = Material.DIAMOND;
            Bukkit.getLogger().severe("This material is not valid, converting to diamond...");
        }
        ItemBuilder builder = new ItemBuilder(mat);
        builder.setName(sec.getString("Name"));
        builder.setLore(sec.getStringList("Lore"));
        ItemStack item2 = builder.getStack();
        if (sec.getBoolean("Enchanted")) {
            EnchantGlow.addGlow(item2);
        }
        return item2;
    }

    public static ItemStack getConfigItemNonLegacy(ConfigurationSection sec, ItemStack item) {
        ItemBuilder builder = new ItemBuilder(item.getType());
        builder.setName(sec.getString("Name"));
        builder.setLore(sec.getStringList("Lore"));
        ItemStack item2 = builder.getStack();
        if (sec.getBoolean("Enchanted")) {
            EnchantGlow.addGlow(item2);
        }
        return item2;
    }

    public static ItemBuilder parseMaterialNonLegacy(String material) {
        String args = material;
        Material mat = Material.getMaterial(args);
        if (mat == null) {
            mat = Material.DIAMOND;
            Bukkit.getLogger().severe("This material is not valid, converting to diamond...");
        }
        ItemBuilder builder = new ItemBuilder(mat);
        return new ItemBuilder(mat);
    }

    public static ItemStack getConfigItemNonLegacy(ConfigurationSection sec, String[] replace, String[] replacements) {
        String args = sec.getString("Material");
        Material mat = Material.getMaterial(args);
        if (mat == null) {
            mat = Material.DIAMOND;
            Bukkit.getLogger().severe("This material is not valid, converting to diamond...");
        }
        ItemBuilder builder = new ItemBuilder(mat);
        String message = sec.getString("Name");
        for (int i = 0; i < replace.length; i++) {
            message = message.replaceAll(replace[i], replacements[i]);
        }
        builder.setName(message);
        List<String> l = Lists.newArrayList();
        for (String lore : sec.getStringList("Lore")) {
            for (int i = 0; i < replace.length; i++) {
                lore = lore.replaceAll(replace[i], replacements[i]);
            }
            l.add(lore);
        }
        builder.setLore(l);
        ItemStack item2 = builder.getStack();
        if (sec.getBoolean("Enchanted")) {
            EnchantGlow.addGlow(item2);
        }
        return item2;
    }

    public static ItemStack getConfigItemNonLegacy(ConfigurationSection sec, ItemStack item, String[] replace, String[] replacements) {
        ItemBuilder builder = new ItemBuilder(item.getType());
        String message = sec.getString("Name");
        for (int i = 0; i < replace.length; i++) {
            message = message.replaceAll(replace[i], replacements[i]);
        }
        builder.setName(message);
        List<String> l = Lists.newArrayList();
        for (String lore : sec.getStringList("Lore")) {
            for (int i = 0; i < replace.length; i++) {
                lore = lore.replaceAll(replace[i], replacements[i]);
            }
            l.add(lore);
        }
        builder.setLore(l);
        ItemStack item2 = builder.getStack();
        if (sec.getBoolean("Enchanted")) {
            EnchantGlow.addGlow(item2);
        }
        return item2;
    }

    public static ItemStack getConfigItemLegacy(ConfigurationSection sec) {
        String[] args = sec.getString("Material").split(";");
        int data;
        try {
            data = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            Bukkit.getLogger().severe("Can not parse Material data.");
            data = 0;
        }
        Material mat = Material.getMaterial(args[0]);
        if (mat == null) {
            mat = Material.DIAMOND;
            return null;
        }
        ItemBuilder builder = new ItemBuilder(mat, data);
        if (mat == Material.SKULL_ITEM) {
            String owner = sec.getString("Owner");
            builder.setOwner(owner);
        }
        builder.setName(sec.getString("Name"));
        builder.setLore(sec.getStringList("Lore"));
        ItemStack item2 = builder.getStack();
        if (sec.getBoolean("Enchanted")) {
            EnchantGlow.addGlow(item2);
        }
        return item2;
    }

    public static ItemStack getConfigItemLegacy(ConfigurationSection sec, ItemStack item) {
        ItemBuilder builder = new ItemBuilder(item.getType(), item.getData().getData());
        builder.setName(sec.getString("Name"));
        builder.setLore(sec.getStringList("Lore"));
        ItemStack item2 = builder.getStack();
        if (sec.getBoolean("Enchanted")) {
            EnchantGlow.addGlow(item2);
        }
        return item2;
    }

    public static ItemBuilder parseMaterialLegacy(String material) {
        String[] args = material.split(";");
        int data;
        try {
            data = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            Bukkit.getLogger().severe("Can not parse Material data.");
            data = 0;
        }
        Material mat = Material.getMaterial(args[0]);
        if (mat == null) {
            mat = Material.DIAMOND;
            Bukkit.getLogger().severe("This material is not valid, converting to diamond...");
        }
        return new ItemBuilder(mat, data);
    }

    public static ItemStack getConfigItemLegacy(ConfigurationSection sec, String[] replace, String[] replacements) {
        String[] args = sec.getString("Material").split(";");
        int data;
        try {
            data = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            Bukkit.getLogger().severe("Can not parse Material data.");
            data = 0;
        }
        Material mat = Material.getMaterial(args[0]);
        if (mat == null) {
            mat = Material.DIAMOND;
            Bukkit.getLogger().severe("This material is not valid, converting to diamond...");
        }
        ItemBuilder builder = new ItemBuilder(mat, data);
        if (mat == Material.SKULL_ITEM) {
            String owner = sec.getString("Owner");
            for (int i = 0; i < replace.length; i++) {
                owner = owner.replaceAll(replace[i], replacements[i]);
            }
            builder.setOwner(owner);
        }
        String message = sec.getString("Name");
        for (int i = 0; i < replace.length; i++) {
            message = message.replaceAll(replace[i], replacements[i]);
        }
        builder.setName(message);
        List<String> l = Lists.newArrayList();
        for (String lore : sec.getStringList("Lore")) {
            for (int i = 0; i < replace.length; i++) {
                lore = lore.replaceAll(replace[i], replacements[i]);
            }
            l.add(lore);
        }
        builder.setLore(l);
        ItemStack item2 = builder.getStack();
        if (sec.getBoolean("Enchanted")) {
            EnchantGlow.addGlow(item2);
        }
        return item2;
    }

    public static ItemStack getConfigItemLegacy(ConfigurationSection sec, ItemStack item, String[] replace, String[] replacements) {
        ItemBuilder builder = new ItemBuilder(item.getType(), item.getData().getData());
        String message = sec.getString("Name");
        for (int i = 0; i < replace.length; i++) {
            message = message.replaceAll(replace[i], replacements[i]);
        }
        builder.setName(message);
        List<String> l = Lists.newArrayList();
        for (String lore : sec.getStringList("Lore")) {
            for (int i = 0; i < replace.length; i++) {
                lore = lore.replaceAll(replace[i], replacements[i]);
            }
            l.add(lore);
        }
        builder.setLore(l);
        ItemStack item2 = builder.getStack();
        if (sec.getBoolean("Enchanted")) {
            EnchantGlow.addGlow(item2);
        }
        return item2;
    }

}
