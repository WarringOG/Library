package com.warring.library.nbt.backend;

import org.bukkit.inventory.ItemStack;

public class NBTItem extends NBTCompound
{
    private ItemStack bukkitItem;
    
    public NBTItem(ItemStack item) {
        super(null, null);
        this.bukkitItem = item.clone();
    }
    
    @Override
    protected Object getCompound() {
        return NBTReflectionUtil.getItemRootNBTTagCompound(NBTReflectionUtil.getNMSItemStack(this.bukkitItem));
    }
    
    @Override
    protected void setCompound(Object tag) {
        this.bukkitItem = NBTReflectionUtil.getBukkitItemStack(NBTReflectionUtil.setNBTTag(tag, NBTReflectionUtil.getNMSItemStack(this.bukkitItem)));
    }
    
    public ItemStack getItem() {
        return this.bukkitItem;
    }
    
    @Override
    protected void setItem(ItemStack item) {
        this.bukkitItem = item;
    }
}
