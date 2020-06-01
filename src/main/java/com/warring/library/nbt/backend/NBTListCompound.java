package com.warring.library.nbt.backend;

import java.util.HashSet;
import java.util.Set;

public class NBTListCompound
{
    private NBTList owner;
    private Object compound;
    
    protected NBTListCompound(NBTList parent, Object obj) {
        this.owner = parent;
        this.compound = obj;
    }
    
    public void setString(String key, String val) {
        if (val == null) {
            this.remove(key);
            return;
        }
        try {
            this.compound.getClass().getMethod("setString", String.class, String.class).invoke(this.compound, key, val);
            this.owner.save();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setInteger(String key, int val) {
        try {
            this.compound.getClass().getMethod("setInt", String.class, Integer.TYPE).invoke(this.compound, key, val);
            this.owner.save();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int getInteger(String key) {
        try {
            return (int)this.compound.getClass().getMethod("getInt", String.class).invoke(this.compound, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public void setDouble(String key, double val) {
        try {
            this.compound.getClass().getMethod("setDouble", String.class, Double.TYPE).invoke(this.compound, key, val);
            this.owner.save();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public double getDouble(String key) {
        try {
            return (double)this.compound.getClass().getMethod("getDouble", String.class).invoke(this.compound, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0.0;
        }
    }
    
    public String getString(String key) {
        try {
            return (String)this.compound.getClass().getMethod("getString", String.class).invoke(this.compound, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
    
    public boolean hasKey(String key) {
        try {
            return (boolean)this.compound.getClass().getMethod("hasKey", String.class).invoke(this.compound, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public Set<String> getKeys() {
        try {
            return (Set<String>)this.compound.getClass().getMethod("c", (Class<?>[])new Class[0]).invoke(this.compound, new Object[0]);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new HashSet<String>();
        }
    }
    
    public void remove(String key) {
        try {
            this.compound.getClass().getMethod("remove", String.class).invoke(this.compound, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
