package com.warring.library.model;

import com.warring.library.utils.RegionUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cuboid {

    private Location primary, second;

    public Cuboid(Location primary, Location secondary) {
        this.second = secondary;
        this.primary = primary;
    }

    public Location getPrimary() {
        return primary;
    }

    public Location getSecond() {
        return second;
    }

    public boolean playerInCuboid(Player p) {
        return RegionUtils.isWithinCuboid(p.getLocation(), primary, second);
    }

    public boolean locationInCuboid(Location loc) {
        return RegionUtils.isWithinCuboid(loc, primary, second);
    }

    public boolean blockInCuboid(Block b) {
        return locationInCuboid(b.getLocation());
    }

    public Block[] getBlocks() {
        return RegionUtils.getBlocks(primary, second);
    }

    public Set<Location> getBox() {
        return RegionUtils.getBoundingBox(primary, second);
    }

    public Set<Block> cuboidEdges() {
        Set<Block> currentList = new HashSet<>();
        int dx = primary.getBlockX() - second.getBlockX();
        int dy = primary.getBlockY() - second.getBlockY();
        int dz = primary.getBlockZ() - second.getBlockZ();

        for (int x = 0; x < dx; ++x) {
            for (int z = 0; z < dz; z++) {
                for (int y = 0; y < dy; y++) {
                    Location loc = new Location(primary.getWorld(), primary.getBlockX() - x, primary.getBlockY() - y, primary.getBlockZ() - z);
                    currentList.add(loc.getBlock());
                }
            }
        }
        return currentList;
    }
}
