package com.warring.library.nbt.backend;

import org.bukkit.entity.Entity;

public class NBTEntity extends NBTCompound
{
    private Entity ent;
    
    public NBTEntity(Entity entity) {
        super(null, null);
        this.ent = entity;
    }
    
    @Override
    protected Object getCompound() {
        return NBTReflectionUtil.getEntityNBTTagCompound(NBTReflectionUtil.getNMSEntity(this.ent));
    }
    
    @Override
    protected void setCompound(Object tag) {
        NBTReflectionUtil.setEntityNBTTag(tag, NBTReflectionUtil.getNMSEntity(this.ent));
    }
}
