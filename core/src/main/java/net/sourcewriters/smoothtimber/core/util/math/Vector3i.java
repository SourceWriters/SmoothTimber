package net.sourcewriters.smoothtimber.core.util.math;

public final class Vector3i {

    private int x, y, z;

    /**
     * Constructs a integer vector and initialises it with all zeros
     */
    public Vector3i() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Constructs a integer vector with the specified values
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a integer vector with the values of the specified double vector
     * 
     * @param vector the double vector
     */
    public Vector3i(Vector3d vector) {
        this.x = (int) vector.getX();
        this.y = (int) vector.getY();
        this.z = (int) vector.getZ();
    }

    /**
     * Sets the x coordinate of this vector
     * 
     * @param x the x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the x coordinate of this vector
     * 
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the y coordinate of this vector
     * 
     * @param y the y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the y coordinate of this vector
     * 
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the z coordinate of this vector
     * 
     * @param z the z coordinate
     */
    public void setZ(int z) {
        this.z = z;
    }

    /**
     * Gets the z coordinate of this vector
     * 
     * @return the z coordinate
     */
    public int getZ() {
        return z;
    }

}
