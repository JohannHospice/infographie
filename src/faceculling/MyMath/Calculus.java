package faceculling.MyMath;


public class Calculus {
    private Calculus() {
    }

    public static Vector normalize(Vector v) {
        double n = Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ());
        return new Vector(v.getX() / n, v.getY() / n, v.getZ() / n);
    }

    public static Vector mirror(Vector v, Vector n) {
        Vector vn = v;//normalize(v);
        Vector nn = n;//normalize(n);
        double cosTheta = dotProduct(vn, nn);
        return new Vector(2 * nn.getX() * cosTheta - vn.getX(),
                2 * nn.getY() * cosTheta - vn.getY(),
                2 * nn.getZ() * cosTheta - vn.getZ());
    }

    public static Vector multiply(Matrix m, Vector v) {
        double[] r = new double[3];
        for (int j = 0; j < 3; j++) {
            r[j] = 0;
            for (int i = 0; i < 3; i++) {
                r[j] += m.get(i, j) * v.get(i);
            }
        }
        return new Vector(r);
    }

    public static Point multiply(Matrix m, Point p) {
        double[] r = new double[4];
        for (int j = 0; j < 4; j++) {
            r[j] = 0;
            for (int i = 0; i < 4; i++) {
                r[j] += m.get(i, j) * p.get(i);
            }
        }
        return new Point(r);
    }

    public static Matrix multiply(Matrix m1, Matrix m2) {
        double[][] values = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                values[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    values[i][j] += m1.get(k, j) * m2.get(i, k);
                }
            }
        }
        return new Matrix(values);
    }

    public static Vector crossProduct(Vector u, Vector v) {
        return new Vector(u.getY() * v.getZ() - u.getZ() * v.getY(),
                u.getZ() * v.getX() - u.getX() * v.getZ(),
                u.getX() * v.getY() - u.getY() * v.getX());
    }

    public static double dotProduct(Vector u, Vector v) {
        return u.getX() * v.getX() + u.getY() * v.getY() + u.getZ() * v.getZ();
    }
}
