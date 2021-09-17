package net.sourcewriters.smoothtimber.core.util.math;

public final class Vector3d {

    private double x, y, z;

    /**
     * Constructs a double vector and initialises it with all zeros
     */
    public Vector3d() {
        this.x = 0D;
        this.y = 0D;
        this.z = 0D;
    }

    /**
     * Constructs a double vector with the specified values
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a double vector with the values of the specified integer vector
     * 
     * @param vector the integer vector
     */
    public Vector3d(Vector3i vector) {
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
    }

    /**
     * Sets the x coordinate of this vector
     * 
     * @param x the x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the x coordinate of this vector
     * 
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the y coordinate of this vector
     * 
     * @param y the y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the y coordinate of this vector
     * 
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the z coordinate of this vector
     * 
     * @param z the z coordinate
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Gets the z coordinate of this vector
     * 
     * @return the z coordinate
     */
    public double getZ() {
        return z;
    }

}
