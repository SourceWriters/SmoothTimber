package net.sourcewriters.smoothtimber.api.util.math;

public final class Cube {

    private final Vector3i origin, min, max;

    public Cube(final Vector3i origin, final int x0, final int y0, final int z0, final int x1, final int y1, final int z1) {
        this.origin = origin;
        this.min = new Vector3i(Math.min(x0, x1), Math.min(y0, y1), Math.min(y0, y1));
        this.max = new Vector3i(Math.max(x0, x1), Math.max(y0, y1), Math.max(y0, y1));
    }

    public Vector3i getOrigin() {
        return origin;
    }

    public Vector3i getMin() {
        return min;
    }

    public Vector3i getMax() {
        return max;
    }

    public int getWidth() {
        return Math.abs(max.getX() - min.getX());
    }

    public int getHeight() {
        return Math.abs(max.getY() - min.getY());
    }

    public int getDepth() {
        return Math.abs(max.getZ() - min.getZ());
    }

}
