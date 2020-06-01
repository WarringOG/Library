package com.warring.library.nbt.backend;

import java.lang.reflect.Method;

public class NBTList
{
    private String listname;
    private NBTCompound parent;
    private NBTType type;
    private Object listobject;
    
    protected NBTList(NBTCompound owner, String name, NBTType type, Object list) {
        this.parent = owner;
        this.listname = name;
        this.type = type;
        this.listobject = list;
        if (type != NBTType.NBTTagString && type != NBTType.NBTTagCompound) {
            System.err.println("List types != String/Compound are currently not implemented!");
        }
    }
    
    protected void save() {
        this.parent.set(this.listname, this.listobject);
    }
    
    public NBTListCompound addCompound() {
        if (this.type != NBTType.NBTTagCompound) {
            new Throwable("Using Compound method on a non Compound list!").printStackTrace();
            return null;
        }
        try {
            Method m = this.listobject.getClass().getMethod("add", NBTReflectionUtil.getNBTBase());
            Object comp = NBTReflectionUtil.getNBTTagCompound().newInstance();
            m.invoke(this.listobject, comp);
            return new NBTListCompound(this, comp);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public NBTListCompound getCompound(int id) {
        if (this.type != NBTType.NBTTagCompound) {
            new Throwable("Using Compound method on a non Compound list!").printStackTrace();
            return null;
        }
        try {
            Method m = this.listobject.getClass().getMethod("get", Integer.TYPE);
            Object comp = m.invoke(this.listobject, id);
            return new NBTListCompound(this, comp);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public String getString(int i) {
        if (this.type != NBTType.NBTTagString) {
            new Throwable("Using String method on a non String list!").printStackTrace();
            return null;
        }
        try {
            Method m = this.listobject.getClass().getMethod("getString", Integer.TYPE);
            return (String)m.invoke(this.listobject, i);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public void addString(String s) {
        if (this.type != NBTType.NBTTagString) {
            new Throwable("Using String method on a non String list!").printStackTrace();
            return;
        }
        try {
            Method m = this.listobject.getClass().getMethod("add", NBTReflectionUtil.getNBTBase());
            m.invoke(this.listobject, NBTReflectionUtil.getNBTTagString().getConstructor(String.class).newInstance(s));
            this.save();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setString(int i, String s) {
        if (this.type != NBTType.NBTTagString) {
            new Throwable("Using String method on a non String list!").printStackTrace();
            return;
        }
        try {
            Method m = this.listobject.getClass().getMethod("a", Integer.TYPE, NBTReflectionUtil.getNBTBase());
            m.invoke(this.listobject, i, NBTReflectionUtil.getNBTTagString().getConstructor(String.class).newInstance(s));
            this.save();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void remove(int i) {
        try {
            Method m = this.listobject.getClass().getMethod(MethodNames.getremoveMethodName(), Integer.TYPE);
            m.invoke(this.listobject, i);
            this.save();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int size() {
        try {
            Method m = this.listobject.getClass().getMethod("size", (Class<?>[])new Class[0]);
            return (int)m.invoke(this.listobject, new Object[0]);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    
    public NBTType getType() {
        return this.type;
    }
}
