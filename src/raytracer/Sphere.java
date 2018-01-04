package raytracer;

import java.awt.*;

public class Sphere {
    public Color color;
    public double radius;
    public Point center;
    public double specular;
    public double specularPower;
    public double lambert;
    public double ambient;
    public double mirror;

    public Sphere(Point center, double radius, Color color,
                  double ambient,
                  double lambert,
                  double specular,
                  double specularPower,
                  double mirror) {
        this.radius = radius;
        this.center = center;
        this.color = color;
        this.ambient = ambient;
        this.lambert = lambert;
        this.specular = specular;
        this.specularPower = specularPower;
        this.mirror = mirror;
    }

    public String toString() {
        return "Sphere(" + radius + "," + center + ")" + " " + color;
    }
}
