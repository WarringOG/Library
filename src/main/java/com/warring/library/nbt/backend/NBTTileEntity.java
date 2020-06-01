package com.warring.library.nbt.backend;

import org.bukkit.block.BlockState;

public class NBTTileEntity extends NBTCompound
{
    private BlockState tile;
    
    public NBTTileEntity(BlockState tile) {
        super(null, null);
        this.tile = tile;
    }
    
    @Override
    protected Object getCompound() {
        return NBTReflectionUtil.getTileEntityNBTTagCompound(this.tile);
    }
    
    @Override
    protected void setCompound(Object tag) {
        NBTReflectionUtil.setTileEntityNBTTagCompound(this.tile, tag);
    }
}
