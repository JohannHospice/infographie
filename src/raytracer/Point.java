package raytracer;

public class Point {
    public double x, y, z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector minus(Point p) {
        return new Vector(x - p.x, y - p.y, z - p.z);
    }

    public Point translate(Vector v) {
        return new Point(x + v.x, y + v.y, z + v.z);
    }

    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
}
