package com.warring.library.nbt.backend;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class ItemNBTAPI extends JavaPlugin
{
    private static boolean compatible;
    public static ItemNBTAPI instance;
    private static String STRING_TEST_KEY = "stringTest";
    private static String INT_TEST_KEY = "intTest";
    private static String DOUBLE_TEST_KEY = "doubleTest";
    private static String BOOLEAN_TEST_KEY = "booleanTest";
    private static String JSON_TEST_KEY = "jsonTest";
    private static String COMP_TEST_KEY = "componentTest";
    private static String SHORT_TEST_KEY = "shortTest";
    private static String BYTE_TEST_KEY = "byteTest";
    private static String FLOAT_TEST_KEY = "floatTest";
    private static String LONG_TEST_KEY = "longTest";
    private static String INTARRAY_TEST_KEY = "intarrayTest";
    private static String BYTEARRAY_TEST_KEY = "bytearrayTest";
    private static String STRING_TEST_VALUE = "TestString";
    private static int INT_TEST_VALUE = 42;
    private static double DOUBLE_TEST_VALUE = 1.5;
    private static boolean BOOLEAN_TEST_VALUE = true;
    private static short SHORT_TEST_VALUE = 64;
    private static byte BYTE_TEST_VALUE = 7;
    private static float FLOAT_TEST_VALUE = 13.37f;
    private static long LONG_TEST_VALUE = 2147483689L;
    private static int[] INTARRAY_TEST_VALUE;
    private static byte[] BYTEARRAY_TEST_VALUE;
    
    public void onEnable() {
        try {
            ItemStack item = new ItemStack(Material.STONE, 1);
            NBTItem nbtItem = new NBTItem(item);
            nbtItem.setString("stringTest", "TestString");
            nbtItem.setInteger("intTest", 42);
            nbtItem.setDouble("doubleTest", 1.5);
            nbtItem.setBoolean("booleanTest", true);
            nbtItem.setByte("byteTest", (byte)7);
            nbtItem.setShort("shortTest", (short)64);
            nbtItem.setLong("longTest", 2147483689L);
            nbtItem.setFloat("floatTest", 13.37f);
            nbtItem.setIntArray("intarrayTest", ItemNBTAPI.INTARRAY_TEST_VALUE);
            nbtItem.setByteArray("bytearrayTest", ItemNBTAPI.BYTEARRAY_TEST_VALUE);
            nbtItem.addCompound("componentTest");
            NBTCompound comp = nbtItem.getCompound("componentTest");
            comp.setString("stringTest", "TestString2");
            comp.setInteger("intTest", 84);
            comp.setDouble("doubleTest", 3.0);
            NBTList list = comp.getList("testlist", NBTType.NBTTagString);
            list.addString("test1");
            list.addString("test2");
            list.addString("test3");
            list.addString("test4");
            list.setString(2, "test42");
            list.remove(1);
            NBTList taglist = comp.getList("complist", NBTType.NBTTagCompound);
            NBTListCompound lcomp = taglist.addCompound();
            lcomp.setDouble("double1", 0.3333);
            lcomp.setInteger("int1", 42);
            lcomp.setString("test1", "test1");
            lcomp.setString("test2", "test2");
            lcomp.remove("test1");
            item = nbtItem.getItem();
            nbtItem = null;
            comp = null;
            list = null;
            nbtItem = new NBTItem(item);
            if (!nbtItem.hasKey("stringTest")) {
                this.getLogger().warning("Wasn't able to check a key! The Item-NBT-API may not work!");
                ItemNBTAPI.compatible = false;
            }
            if (!"TestString".equals(nbtItem.getString("stringTest")) || nbtItem.getInteger("intTest") != 42 || nbtItem.getDouble("doubleTest") != 1.5 || nbtItem.getByte("byteTest") != 7 || nbtItem.getShort("shortTest") != 64 || nbtItem.getFloat("floatTest") != 13.37f || nbtItem.getLong("longTest") != 2147483689L || nbtItem.getIntArray("intarrayTest").length != ItemNBTAPI.INTARRAY_TEST_VALUE.length || nbtItem.getByteArray("bytearrayTest").length != ItemNBTAPI.BYTEARRAY_TEST_VALUE.length || !nbtItem.getBoolean("booleanTest").equals(true)) {
                this.getLogger().warning("One key does not equal the original value! The Item-NBT-API may not work!");
                ItemNBTAPI.compatible = false;
            }
            nbtItem.setString("stringTest", null);
            if (nbtItem.getKeys().size() != 10) {
                this.getLogger().warning("Wasn't able to remove a key (Got " + nbtItem.getKeys().size() + " when expecting 4)! The Item-NBT-API may not work!");
                ItemNBTAPI.compatible = false;
            }
            comp = nbtItem.getCompound("componentTest");
            if (comp == null) {
                this.getLogger().warning("Wasn't able to get the NBTCompound! The Item-NBT-API may not work!");
                ItemNBTAPI.compatible = false;
            }
            if (!comp.hasKey("stringTest")) {
                this.getLogger().warning("Wasn't able to check a compound key! The Item-NBT-API may not work!");
                ItemNBTAPI.compatible = false;
            }
            if (!"TestString2".equals(comp.getString("stringTest")) || comp.getInteger("intTest") != 84 || comp.getDouble("doubleTest") != 3.0 || comp.getBoolean("booleanTest")) {
                this.getLogger().warning("One key does not equal the original compound value! The Item-NBT-API may not work!");
                ItemNBTAPI.compatible = false;
            }
            list = comp.getList("testlist", NBTType.NBTTagString);
            if (comp.getType("testlist") != NBTType.NBTTagList) {
                this.getLogger().warning("Wasn't able to get the correct Tag type! The Item-NBT-API may not work!");
                ItemNBTAPI.compatible = false;
            }
            if (!list.getString(1).equals("test42") || list.size() != 3) {
                this.getLogger().warning("The List support got an error, and may not work!");
            }
            taglist = comp.getList("complist", NBTType.NBTTagCompound);
            if (taglist.size() == 1) {
                lcomp = taglist.getCompound(0);
                if (lcomp.getKeys().size() != 3) {
                    this.getLogger().warning("Wrong key amount in Taglist (" + lcomp.getKeys().size() + ")! The Item-NBT-API may not work!");
                    ItemNBTAPI.compatible = false;
                }
                else if (lcomp.getDouble("double1") != 0.3333 || lcomp.getInteger("int1") != 42 || !lcomp.getString("test2").equals("test2") || lcomp.hasKey("test1")) {
                    this.getLogger().warning("One key in the Taglist changed! The Item-NBT-API may not work!");
                    ItemNBTAPI.compatible = false;
                }
            }
            else {
                this.getLogger().warning("Taglist is empty! The Item-NBT-API may not work!");
                ItemNBTAPI.compatible = false;
            }
        }
        catch (Exception ex) {
            this.getLogger().log(Level.SEVERE, null, ex);
            ItemNBTAPI.compatible = false;
        }
        this.testJson();
    }
    
    public void testJson() {
        try {
            ItemStack item = new ItemStack(Material.STONE, 1);
            NBTItem nbtItem = new NBTItem(item);
            nbtItem.setObject("jsonTest", new SimpleJsonTestObject());
            item = nbtItem.getItem();
            if (!nbtItem.hasKey("jsonTest")) {
                this.getLogger().warning("Wasn't able to find JSON key! The Item-NBT-API may not work with Json serialization/deserialization!");
            }
            else {
                SimpleJsonTestObject simpleObject = nbtItem.getObject("jsonTest", SimpleJsonTestObject.class);
                if (simpleObject == null) {
                    this.getLogger().warning("Wasn't able to check JSON key! The Item-NBT-API may not work with Json serialization/deserialization!");
                }
                else if (!"TestString".equals(simpleObject.getTestString()) || simpleObject.getTestInteger() != 42 || simpleObject.getTestDouble() != 1.5 || !simpleObject.isTestBoolean()) {
                    this.getLogger().warning("One key does not equal the original value in JSON! The Item-NBT-API may not work with Json serialization/deserialization!");
                }
            }
        }
        catch (Exception ex) {
            this.getLogger().log(Level.SEVERE, null, ex);
            this.getLogger().warning(ex.getMessage());
        }
    }
    
    public void onDisable() {
    }
    
    public boolean isCompatible() {
        return ItemNBTAPI.compatible;
    }
    
    public static NBTItem getNBTItem(ItemStack item) {
        return new NBTItem(item);
    }
    
    static {
        ItemNBTAPI.compatible = true;
        INTARRAY_TEST_VALUE = new int[] { 1337, 42, 69 };
        BYTEARRAY_TEST_VALUE = new byte[] { 8, 7, 3, 2 };
    }
    
    public static class SimpleJsonTestObject
    {
        private String testString;
        private int testInteger;
        private double testDouble;
        private boolean testBoolean;
        
        public SimpleJsonTestObject() {
            this.testString = "TestString";
            this.testInteger = 42;
            this.testDouble = 1.5;
            this.testBoolean = true;
        }
        
        public String getTestString() {
            return this.testString;
        }
        
        public void setTestString(String testString) {
            this.testString = testString;
        }
        
        public int getTestInteger() {
            return this.testInteger;
        }
        
        public void setTestInteger(int testInteger) {
            this.testInteger = testInteger;
        }
        
        public double getTestDouble() {
            return this.testDouble;
        }
        
        public void setTestDouble(double testDouble) {
            this.testDouble = testDouble;
        }
        
        public boolean isTestBoolean() {
            return this.testBoolean;
        }
        
        public void setTestBoolean(boolean testBoolean) {
            this.testBoolean = testBoolean;
        }
    }
}
