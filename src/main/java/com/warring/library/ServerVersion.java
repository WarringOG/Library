package com.warring.library;

import org.bukkit.Bukkit;

import java.util.logging.Level;

public enum ServerVersion {

    Unknown(0),
    v1_7_R4(174),
    v1_8_R3(183),
    v1_9_R1(191),
    v1_9_R2(192),
    v1_10_R1(1101),
    v1_11_R1(1111),
    v1_12_R1(1121),
    v1_13_R1(1131),
    v1_13_R2(1132),
    v1_14_R1(1141),
    v1_15_R1(1151);

    private static ServerVersion serverVersion;
    private int versionId;

    ServerVersion(int versionId) {
        this.versionId = versionId;
    }

    public static ServerVersion getVersion() {
        if (serverVersion != null) {
            return serverVersion;
        }
        String versionOfPackage = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            serverVersion = ServerVersion.valueOf(versionOfPackage);
        } catch (IllegalArgumentException ex) {
            serverVersion = ServerVersion.Unknown;
        }
        if (serverVersion != Unknown) {
            Bukkit.getLogger().log(Level.INFO, String.format("Successfully connected to version %s'!", serverVersion.name()));
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "Was not able to find the server version, this might result in errors.");
        }
        return serverVersion;
    }

    public static boolean isOver_V1_12() {
        return getVersion().getVersionId() > 1121;
    }

    public int getVersionId() {
        return versionId;
    }

    public boolean isThis() {
        return this.versionId == getVersion().versionId;
    }
}
