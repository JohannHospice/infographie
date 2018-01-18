package app.modele.math;


public class Vector extends Abstract4D {
    public Vector(double[] v) {
        super(v);
    }

    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    public String toString() {
        return "[" + getX() + "," + getY() + "," + getZ() + "]";
    }
}
