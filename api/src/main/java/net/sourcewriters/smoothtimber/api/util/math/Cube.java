package net.sourcewriters.smoothtimber.api.util.math;

public final class Cube {

    private final Vector3i origin, min, max;

    public Cube(Vector3i origin, int x0, int y0, int z0, int x1, int y1, int z1) {
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
