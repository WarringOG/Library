package com.warring.library.nbt;

import com.warring.library.nbt.backend.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NBTapi
{
    public static ItemStack addNBTTag(String type, String arguments, ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        nbti.setString(type, arguments);
        return nbti.getItem();
    }

    public static NBTItem addNBTTag(String type, String arguments, NBTItem i) {
        i.setString(type, arguments);
        return i;
    }

    public static NBTItem addNBTTag(NBTItem i, String arg) {
        i.addCompound(arg);
        return i;
    }

    public static NBTItem getNBTItem(ItemStack i) {
        return new NBTItem(i);
    }
    
    public static boolean contains(String type, ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        return nbti.hasKey(type);
    }
    
    public static String get(String argument, ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        return nbti.getString(argument);
    }

    public static int getInt(String arg, ItemStack i) {
        NBTItem nbti = new NBTItem(i);
        return nbti.getInteger(arg);
    }
    
    public static boolean hasNBT(ItemStack i) {
        if (i == null) {
            return false;
        }
        if (i.getType().equals((Object) Material.AIR)) {
            return false;
        }
        NBTItem nbti = new NBTItem(i);
        return nbti.getKeys().size() != 0;
    }
}
