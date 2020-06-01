package com.warring.library.nbt.backend;

import com.warring.library.ServerVersion;

public class MethodNames
{
    protected static String getTiledataMethodName() {
        ServerVersion v = ServerVersion.getVersion();
        if (v == ServerVersion.v1_8_R3) {
            return "b";
        }
        return "save";
    }
    
    protected static String getTypeMethodName() {
        ServerVersion v = ServerVersion.getVersion();
        if (v == ServerVersion.v1_8_R3) {
            return "b";
        }
        return "d";
    }
    
    protected static String getEntitynbtgetterMethodName() {
        ServerVersion v = ServerVersion.getVersion();
        return "b";
    }
    
    protected static String getEntitynbtsetterMethodName() {
        ServerVersion v = ServerVersion.getVersion();
        return "a";
    }
    
    protected static String getremoveMethodName() {
        ServerVersion v = ServerVersion.getVersion();
        if (v == ServerVersion.v1_8_R3) {
            return "a";
        }
        return "remove";
    }
}
