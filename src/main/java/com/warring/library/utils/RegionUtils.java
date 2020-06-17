package com.warring.library.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegionUtils {

    public static double BOUNDING_VERTICAL_GAP = 1;

    public static double BOUNDING_HORIZONTAL_GAP = 1;

	public static boolean isWithinCuboid(final Location location, final Location primary, final Location secondary) {
		return isWithinCuboid(location, primary.toVector(), secondary.toVector());
	}

	public static boolean isWithinCuboid(final Location location, final Vector primary, final Vector secondary) {
		final double locX = location.getX();
		final double locY = location.getY();
		final double locZ = location.getZ();

		final int x = primary.getBlockX();
		final int y = primary.getBlockY();
		final int z = primary.getBlockZ();

		final int x1 = secondary.getBlockX();
		final int y1 = secondary.getBlockY();
		final int z1 = secondary.getBlockZ();

		if (locX >= x && locX <= x1 || locX <= x && locX >= x1)
			if (locZ >= z && locZ <= z1 || locZ <= z && locZ >= z1)
				if (locY >= y && locY <= y1 || locY <= y && locY >= y1)
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

        for (int x = bottomBlockX; x <= topBlockX + 1; x++)
            for (int z = bottomBlockZ; z <= topBlockZ + 1; z++)
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = primary.getWorld().getBlockAt(x, y, z);

                    if (block != null)
                        blocks.add(block);
                }

        return blocks.toArray( new Block[ blocks.size() ] );
    }

    private static VectorHelper getMinimumPoint(final Location pos1, final Location pos2) {
        return new VectorHelper(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
    }

    private static VectorHelper getMaximumPoint(final Location pos1, final Location pos2) {
        return new VectorHelper(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));
    }

    private static int getHeight(final Location pos1, final Location pos2) {
        final VectorHelper min = getMinimumPoint(pos1, pos2);
        final VectorHelper max = getMaximumPoint(pos1, pos2);

        return (int) (max.getY() - min.getY() + 1.0D);
    }


    public static Set<Location> getBoundingBox(final Location primary, final Location secondary) {
        final List<VectorHelper> shape = new ArrayList<>();

        final VectorHelper min = getMinimumPoint(primary, secondary);
        final VectorHelper max = getMaximumPoint(primary, secondary).add(1, 0, 1);

        final int height = getHeight(primary, secondary);

        final List<VectorHelper> bottomCorners = new ArrayList<>();

        bottomCorners.add(new VectorHelper(min.getX(), min.getY(), min.getZ()));
        bottomCorners.add(new VectorHelper(max.getX(), min.getY(), min.getZ()));
        bottomCorners.add(new VectorHelper(max.getX(), min.getY(), max.getZ()));
        bottomCorners.add(new VectorHelper(min.getX(), min.getY(), max.getZ()));

        for (int i = 0; i < bottomCorners.size(); i++) {
            final VectorHelper p1 = bottomCorners.get(i);
            final VectorHelper p2 = i + 1 < bottomCorners.size() ? (VectorHelper) bottomCorners.get(i + 1) : (VectorHelper) bottomCorners.get(0);

            final VectorHelper p3 = p1.add(0, height, 0);
            final VectorHelper p4 = p2.add(0, height, 0);
            shape.addAll(plotLine(p1, p2));
            shape.addAll(plotLine(p3, p4));
            shape.addAll(plotLine(p1, p3));

            for (double offset = BOUNDING_VERTICAL_GAP; offset < height; offset += BOUNDING_VERTICAL_GAP) {
                final VectorHelper p5 = p1.add(0.0D, offset, 0.0D);
                final VectorHelper p6 = p2.add(0.0D, offset, 0.0D);
                shape.addAll(plotLine(p5, p6));
            }
        }

        final Set<Location> locations = new HashSet<>();

        for (final VectorHelper vector : shape)
            locations.add(new Location(primary.getWorld(), vector.getX(), vector.getY(), vector.getZ()));

        return locations;
    }

    private static List<VectorHelper> plotLine(final VectorHelper p1, final VectorHelper p2) {
        final List<VectorHelper> ShapeVectors = new ArrayList<>();

        final int points = (int) (p1.distance(p2) / BOUNDING_HORIZONTAL_GAP) + 1;
        final double length = p1.distance(p2);
        final double gap = length / (points - 1);

        final VectorHelper gapShapeVector = p2.subtract(p1).normalize().multiply(gap);

        for (int i = 0; i < points; i++) {
            final VectorHelper currentPoint = p1.add(gapShapeVector.multiply(i));

            ShapeVectors.add(currentPoint);
        }

        return ShapeVectors;
    }

    @RequiredArgsConstructor
    private final static class VectorHelper {

        @Getter
        protected final double x, y, z;

        public VectorHelper add(final VectorHelper other) {
            return add(other.x, other.y, other.z);
        }

        public VectorHelper add(final double x, final double y, final double z) {
            return new VectorHelper(this.x + x, this.y + y, this.z + z);
        }

        public VectorHelper subtract(final VectorHelper other) {
            return subtract(other.x, other.y, other.z);
        }

        public VectorHelper subtract(final double x, final double y, final double z) {
            return new VectorHelper(this.x - x, this.y - y, this.z - z);
        }

        public VectorHelper multiply(final double n) {
            return new VectorHelper(this.x * n, this.y * n, this.z * n);
        }

        public VectorHelper divide(final double n) {
            return new VectorHelper(x / n, y / n, z / n);
        }

        public double length() {
            return Math.sqrt(x * x + y * y + z * z);
        }

        public double distance(final VectorHelper other) {
            return Math.sqrt(Math.pow(other.x - x, 2) +
                    Math.pow(other.y - y, 2) +
                    Math.pow(other.z - z, 2));
        }

        public VectorHelper normalize() {
            return divide(length());
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof VectorHelper))
                return false;

            final VectorHelper other = (VectorHelper) obj;
            return other.x == this.x && other.y == this.y && other.z == this.z;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ", " + z + ")";
        }
    }
}
