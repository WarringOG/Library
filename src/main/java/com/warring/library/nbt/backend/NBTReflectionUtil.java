package com.warring.library.nbt.backend;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.warring.library.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.Stack;

public class NBTReflectionUtil
{
    private static Gson gson;
    private static String version;
    
    private static Class getCraftItemStack() {
        try {
            Class c = Class.forName("org.bukkit.craftbukkit." + NBTReflectionUtil.version + ".inventory.CraftItemStack");
            return c;
        }
        catch (Exception ex) {
            System.out.println("Error in WarringPlugin NBTAPI(beta test). Report this to developer.");
            ex.printStackTrace();
            return null;
        }
    }
    
    private static Class getCraftEntity() {
        try {
            Class c = Class.forName("org.bukkit.craftbukkit." + NBTReflectionUtil.version + ".entity.CraftEntity");
            return c;
        }
        catch (Exception ex) {
            System.out.println("Error in WarringPlugin NBTAPI(beta test). Report this to developer.");
            ex.printStackTrace();
            return null;
        }
    }
    
    protected static Class getNBTBase() {
        try {
            Class c = Class.forName("net.minecraft.server." + NBTReflectionUtil.version + ".NBTBase");
            return c;
        }
        catch (Exception ex) {
            System.out.println("Error in WarringPlugin NBTAPI(beta test). Report this to developer.");
            ex.printStackTrace();
            return null;
        }
    }
    
    protected static Class getNBTTagString() {
        try {
            Class c = Class.forName("net.minecraft.server." + NBTReflectionUtil.version + ".NBTTagString");
            return c;
        }
        catch (Exception ex) {
            System.out.println("Error in WarringPlugin NBTAPI(beta test). Report this to developer.");
            ex.printStackTrace();
            return null;
        }
    }
    
    protected static Class getNBTTagCompound() {
        try {
            Class c = Class.forName("net.minecraft.server." + NBTReflectionUtil.version + ".NBTTagCompound");
            return c;
        }
        catch (Exception ex) {
            System.out.println("Error in WarringPlugin NBTAPI(beta test). Report this to developer.");
            ex.printStackTrace();
            return null;
        }
    }
    
    protected static Class getTileEntity() {
        try {
            Class c = Class.forName("net.minecraft.server." + NBTReflectionUtil.version + ".TileEntity");
            return c;
        }
        catch (Exception ex) {
            System.out.println("Error in WarringPlugin NBTAPI(beta test). Report this to developer.");
            ex.printStackTrace();
            return null;
        }
    }
    
    protected static Class getCraftWorld() {
        try {
            Class c = Class.forName("org.bukkit.craftbukkit." + NBTReflectionUtil.version + ".CraftWorld");
            return c;
        }
        catch (Exception ex) {
            System.out.println("Error in WarringPlugin NBTAPI(beta test). Report this to developer.");
            ex.printStackTrace();
            return null;
        }
    }
    
    private static Object getNewNBTTag() {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            Class c = Class.forName("net.minecraft.server." + version + ".NBTTagCompound");
            return c.newInstance();
        }
        catch (Exception ex) {
            System.out.println("Error in WarringPlugin NBTAPI(beta test). Report this to developer.");
            ex.printStackTrace();
            return null;
        }
    }
    
    private static Object getnewBlockPosition(int x, int y, int z) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            Class c = Class.forName("net.minecraft.server." + version + ".BlockPosition");
            return c.getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(x, y, z);
        }
        catch (Exception ex) {
            System.out.println("Error in WarringPlugin NBTAPI(beta test). Report this to developer.");
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Object setNBTTag(Object NBTTag, Object NMSItem) {
        try {
            Method method = NMSItem.getClass().getMethod("setTag", NBTTag.getClass());
            method.invoke(NMSItem, NBTTag);
            return NMSItem;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Object getNMSItemStack(ItemStack item) {
        Class cis = getCraftItemStack();
        try {
            Method method = cis.getMethod("asNMSCopy", ItemStack.class);
            Object answer = method.invoke(cis, item);
            return answer;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Object getNMSEntity(Entity entity) {
        Class cis = getCraftEntity();
        try {
            Method method = cis.getMethod("getHandle", (Class[])new Class[0]);
            return method.invoke(getCraftEntity().cast(entity), new Object[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static ItemStack getBukkitItemStack(Object item) {
        Class cis = getCraftItemStack();
        try {
            Method method = cis.getMethod("asCraftMirror", item.getClass());
            Object answer = method.invoke(cis, item);
            return (ItemStack)answer;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Object getItemRootNBTTagCompound(Object nmsitem) {
        Class c = nmsitem.getClass();
        try {
            Method method = c.getMethod("getTag", (Class[])new Class[0]);
            Object answer = method.invoke(nmsitem, new Object[0]);
            return answer;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Object getEntityNBTTagCompound(Object nmsitem) {
        Class c = nmsitem.getClass();
        try {
            Method method = c.getMethod(MethodNames.getEntitynbtgetterMethodName(), getNBTTagCompound());
            Object nbt = getNBTTagCompound().newInstance();
            Object answer = method.invoke(nmsitem, nbt);
            if (answer == null) {
                answer = nbt;
            }
            return answer;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Object setEntityNBTTag(Object NBTTag, Object NMSItem) {
        try {
            Method method = NMSItem.getClass().getMethod(MethodNames.getEntitynbtsetterMethodName(), getNBTTagCompound());
            method.invoke(NMSItem, NBTTag);
            return NMSItem;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Object getTileEntityNBTTagCompound(BlockState tile) {
        try {
            Object pos = getnewBlockPosition(tile.getX(), tile.getY(), tile.getZ());
            Object cworld = getCraftWorld().cast(tile.getWorld());
            Object nmsworld = cworld.getClass().getMethod("getHandle", (Class<?>[])new Class[0]).invoke(cworld, new Object[0]);
            Object o = nmsworld.getClass().getMethod("getTileEntity", pos.getClass()).invoke(nmsworld, pos);
            Method method = getTileEntity().getMethod(MethodNames.getTiledataMethodName(), getNBTTagCompound());
            Object tag = getNBTTagCompound().newInstance();
            Object answer = method.invoke(o, tag);
            if (answer == null) {
                answer = tag;
            }
            return answer;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void setTileEntityNBTTagCompound(BlockState tile, Object comp) {
        try {
            Object pos = getnewBlockPosition(tile.getX(), tile.getY(), tile.getZ());
            Object cworld = getCraftWorld().cast(tile.getWorld());
            Object nmsworld = cworld.getClass().getMethod("getHandle", (Class<?>[])new Class[0]).invoke(cworld, new Object[0]);
            Object o = nmsworld.getClass().getMethod("getTileEntity", pos.getClass()).invoke(nmsworld, pos);
            Method method = getTileEntity().getMethod("a", getNBTTagCompound());
            method.invoke(o, comp);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Object getSubNBTTagCompound(Object compound, String name) {
        Class c = compound.getClass();
        try {
            Method method = c.getMethod("getCompound", String.class);
            Object answer = method.invoke(compound, name);
            return answer;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void addNBTTagCompound(NBTCompound comp, String name) {
        if (name == null) {
            remove(comp, name);
            return;
        }
        Object nbttag = comp.getCompound();
        if (nbttag == null) {
            nbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return;
        }
        Object workingtag = gettoCompount(nbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("set", String.class, getNBTBase());
            method.invoke(workingtag, name, getNBTTagCompound().newInstance());
            comp.setCompound(nbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Boolean valideCompound(NBTCompound comp) {
        Object root = comp.getCompound();
        if (root == null) {
            root = getNewNBTTag();
        }
        return gettoCompount(root, comp) != null;
    }
    
    private static Object gettoCompount(Object nbttag, NBTCompound comp) {
        Stack<String> structure = new Stack<String>();
        while (comp.getParent() != null) {
            structure.add(comp.getName());
            comp = comp.getParent();
        }
        while (!structure.isEmpty()) {
            nbttag = getSubNBTTagCompound(nbttag, structure.pop());
            if (nbttag == null) {
                return null;
            }
        }
        return nbttag;
    }
    
    public static void setString(NBTCompound comp, String key, String text) {
        if (text == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("setString", String.class, String.class);
            method.invoke(workingtag, key, text);
            comp.setCompound(rootnbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static String getString(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("getString", String.class);
            return (String)method.invoke(workingtag, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static String getContent(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("get", String.class);
            return method.invoke(workingtag, key).toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void setInt(NBTCompound comp, String key, Integer i) {
        if (i == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("setInt", String.class, Integer.TYPE);
            method.invoke(workingtag, key, i);
            comp.setCompound(rootnbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Integer getInt(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("getInt", String.class);
            return (Integer)method.invoke(workingtag, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void setByteArray(NBTCompound comp, String key, byte[] b) {
        if (b == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("setByteArray", String.class, byte[].class);
            method.invoke(workingtag, key, b);
            comp.setCompound(rootnbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static byte[] getByteArray(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("getByteArray", String.class);
            return (byte[])method.invoke(workingtag, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void setIntArray(NBTCompound comp, String key, int[] i) {
        if (i == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("setIntArray", String.class, int[].class);
            method.invoke(workingtag, key, i);
            comp.setCompound(rootnbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static int[] getIntArray(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("getIntArray", String.class);
            return (int[])method.invoke(workingtag, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void setFloat(NBTCompound comp, String key, Float f) {
        if (f == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("setFloat", String.class, Float.TYPE);
            method.invoke(workingtag, key, f);
            comp.setCompound(rootnbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Float getFloat(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("getFloat", String.class);
            return (Float)method.invoke(workingtag, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void setLong(NBTCompound comp, String key, Long f) {
        if (f == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("setLong", String.class, Long.TYPE);
            method.invoke(workingtag, key, f);
            comp.setCompound(rootnbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Long getLong(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("getLong", String.class);
            return (Long)method.invoke(workingtag, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void setShort(NBTCompound comp, String key, Short f) {
        if (f == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("setShort", String.class, Short.TYPE);
            method.invoke(workingtag, key, f);
            comp.setCompound(rootnbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Short getShort(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("getShort", String.class);
            return (Short)method.invoke(workingtag, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void setByte(NBTCompound comp, String key, Byte f) {
        if (f == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("setByte", String.class, Byte.TYPE);
            method.invoke(workingtag, key, f);
            comp.setCompound(rootnbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Byte getByte(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("getByte", String.class);
            return (Byte)method.invoke(workingtag, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void setDouble(NBTCompound comp, String key, Double d) {
        if (d == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("setDouble", String.class, Double.TYPE);
            method.invoke(workingtag, key, d);
            comp.setCompound(rootnbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Double getDouble(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("getDouble", String.class);
            return (Double)method.invoke(workingtag, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static byte getType(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return 0;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod(MethodNames.getTypeMethodName(), String.class);
            return (byte)method.invoke(workingtag, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static void setBoolean(NBTCompound comp, String key, Boolean d) {
        if (d == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("setBoolean", String.class, Boolean.TYPE);
            method.invoke(workingtag, key, d);
            comp.setCompound(rootnbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Boolean getBoolean(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("getBoolean", String.class);
            return (Boolean)method.invoke(workingtag, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void set(NBTCompound comp, String key, Object val) {
        if (val == null) {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            new Throwable("InvalideCompound").printStackTrace();
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("set", String.class, getNBTBase());
            method.invoke(workingtag, key, val);
            comp.setCompound(rootnbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static NBTList getList(NBTCompound comp, String key, NBTType type) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("getList", String.class, Integer.TYPE);
            return new NBTList(comp, key, type, method.invoke(workingtag, key, type.getId()));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void setObject(NBTCompound comp, String key, Object value) {
        try {
            String json = NBTReflectionUtil.gson.toJson(value);
            setString(comp, key, json);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static <T> T getObject(NBTCompound comp, String key, Class<T> type) {
        String json = getString(comp, key);
        if (json == null) {
            return null;
        }
        try {
            return deserializeJson(json, type);
        }
        catch (JsonSyntaxException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static <T> T deserializeJson(String json, Class<T> type) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }
        T obj = (T)NBTReflectionUtil.gson.fromJson(json, (Class)type);
        return type.cast(obj);
    }
    
    public static void remove(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("remove", String.class);
            method.invoke(workingtag, key);
            comp.setCompound(rootnbttag);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Boolean hasKey(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("hasKey", String.class);
            return (Boolean)method.invoke(workingtag, key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Set<String> getKeys(NBTCompound comp) {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null) {
            rootnbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) {
            return null;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        try {
            Method method = workingtag.getClass().getMethod("c", (Class<?>[])new Class[0]);
            return (Set<String>)method.invoke(workingtag, new Object[0]);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    static {
        gson = new Gson();
        version = ServerVersion.getVersion().name();
    }
}
