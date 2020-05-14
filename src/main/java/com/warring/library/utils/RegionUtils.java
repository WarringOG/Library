package com.warring.library.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class RegionUtils {

    public static boolean isWithinCuboid(Location loc, Location primary, Location secondary) {
        Vector primVector = primary.toVector(), secVector = secondary.toVector();

        double locX = loc.getX();
        double locY = loc.getY();
        double locZ = loc.getZ();

        int x = primVector.getBlockX();
        int y = primVector.getBlockY();
        int z = primVector.getBlockZ();

        int x1 = secVector.getBlockX();
        int y1 = secVector.getBlockY();
        int z1 = secVector.getBlockZ();

        if ((locX >= x && locX <= x1) || (locX <= x && locX >= x1))
            if ((locZ >= z && locZ <= z1) || (locZ <= z && locZ >= z1))
                if ((locY >= y && locY <= y1) || (locY <= y && locY >= y1))
                    return true;

        return false;
    }

    public static Block[] getBlocks(Location primary, Location secondary) {
        List<Block> blocks = new ArrayList<>();

        int topBlockX = (primary.getBlockX() < secondary.getBlockX() ? secondary.getBlockX() : primary.getBlockX());
        int bottomBlockX = (primary.getBlockX() > secondary.getBlockX() ? secondary.getBlockX() : primary.getBlockX());
        int topBlockY = (primary.getBlockY() < secondary.getBlockY() ? secondary.getBlockY() : primary.getBlockY());
        int bottomBlockY = (primary.getBlockY() > secondary.getBlockY() ? secondary.getBlockY() : primary.getBlockY());
        int topBlockZ = (primary.getBlockZ() < secondary.getBlockZ() ? secondary.getBlockZ() : primary.getBlockZ());
        int bottomBlockZ = (primary.getBlockZ() > secondary.getBlockZ() ? secondary.getBlockZ() : primary.getBlockZ());

        for (int x = bottomBlockX; x <= topBlockX; x++)
            for (int z = bottomBlockZ; z <= topBlockZ; z++)
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = primary.getWorld().getBlockAt(x, y, z);

                    if (block != null)
                        blocks.add(block);
                }

        return blocks.toArray( new Block[ blocks.size() ] );
    }
}
