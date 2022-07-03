package net.sourcewriters.smoothtimber.api.util.math;

public final class Cube {

    private final Vector3i origin, min, max;

    /**
     * Creates a cube based on the origin and two points
     * 
     * @param origin the origin of the cube
     * @param x0     first x coordinate
     * @param y0     first y coordinate
     * @param z0     first z coordinate
     * @param x1     second x coordinate
     * @param y1     second y coordinate
     * @param z1     second z coordinate
     */
    public Cube(final Vector3i origin, final int x0, final int y0, final int z0, final int x1, final int y1, final int z1) {
        this.origin = origin;
        this.min = new Vector3i(Math.min(x0, x1), Math.min(y0, y1), Math.min(z0, z1));
        this.max = new Vector3i(Math.max(x0, x1), Math.max(y0, y1), Math.max(z0, z1));
    }

    /**
     * Gets the origin of the cube
     * 
     * @return the origin
     */
    public Vector3i getOrigin() {
        return origin;
    }

    /**
     * Gets the bottom left corner of the cube
     * 
     * @return the bottom left corner
     */
    public Vector3i getMin() {
        return min;
    }

    /**
     * Gets the top right corner of the cube
     * 
     * @return the top right corner
     */
    public Vector3i getMax() {
        return max;
    }

    /**
     * Gets the width of the cube
     * 
     * @return the width
     */
    public int getWidth() {
        return Math.abs(max.getX() - min.getX());
    }

    /**
     * Gets the height of the cube
     * 
     * @return the height
     */
    public int getHeight() {
        return Math.abs(max.getY() - min.getY());
    }

    /**
     * Gets the depth of the cube
     * 
     * @return the depth
     */
    public int getDepth() {
        return Math.abs(max.getZ() - min.getZ());
    }

}
