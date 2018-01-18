package app.modele.math;

public class VectorXYZ extends VectorXY {
    public float z;

    public VectorXYZ(int x, int y, float z) {
        super(x, y);
        this.z = z;
    }
}
