package com.warring.library.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

public class EnchantGlow extends EnchantmentWrapper {
    private static Enchantment glow;

    private EnchantGlow() {
        super(255);
    }

    public static void addGlow(ItemStack item) {
        try {
            Enchantment glow = getGlow();

            item.addEnchantment(Objects.requireNonNull(glow), 1);
        } catch (Exception ignored) {
        }
    }

    public static Enchantment getGlow() {
        try {
            if (EnchantGlow.glow != null) {
                return EnchantGlow.glow;
            }

            Field f = Enchantment.class.getDeclaredField("acceptingNew");

            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(EnchantGlow.glow = new EnchantGlow());

            return EnchantGlow.glow;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    public EnchantmentTarget getItemTarget() {
        return null;
    }

    public int getMaxLevel() {
        return 10;
    }

    public String getName() {
        return "Glow";
    }

    public int getStartLevel() {
        return 1;
    }
}
