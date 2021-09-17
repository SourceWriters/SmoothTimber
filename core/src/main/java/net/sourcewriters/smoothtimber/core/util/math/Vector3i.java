package net.sourcewriters.smoothtimber.core.util.math;

public final class Vector3i {

    private int x, y, z;

    public Vector3i() {}

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3i(Vector3d vector) {
        this.x = (int) vector.getX();
        this.y = (int) vector.getY();
        this.z = (int) vector.getZ();
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getZ() {
        return z;
    }

}
