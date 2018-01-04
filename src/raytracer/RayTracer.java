package raytracer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RayTracer implements Runnable {
    private MyPanel panel;
    private BufferedImage image;
    private Graphics graphics;
    private List<Sphere> objects;
    private Vector light = new Vector(100, 0, 0);
    private Color lightColor = new Color(255, 255, 255);
    private Color ambientColor = new Color(255, 255, 255);

    {
        light.normalize();
    }

    public RayTracer(MyPanel p) {
        panel = p;
        image = new BufferedImage(panel.getPreferredSize().width,
                panel.getPreferredSize().height,
                BufferedImage.TYPE_INT_ARGB);
        graphics = image.getGraphics();
        panel.setImage(image);
        objects = new ArrayList<>();
        objects.add(new Sphere(new Point(-500, 0, -8000), 1000, Color.WHITE,
                0.0, 0.4, 0.8, 2.0, 2));
        objects.add(new Sphere(new Point(-500, 0, -3600), 300, Color.BLUE,
                0.1, 0.2, 0.5, 10.0, 1));
        objects.add(new Sphere(new Point(0, 500, -3600), 300, Color.WHITE,
                0.0, 0.1, 0.1, 20.0, 0.5));
        // H2O
        objects.add(new Sphere(new Point(1000, 0, -4000), 800, Color.RED,
                0.1, 0.8, 0.2, 10., 0));
        objects.add(new Sphere(new Point(600, -200, -4000), 600, Color.GRAY,
                0.1, 0.8, 0.2, 10., 0));
        objects.add(new Sphere(new Point(1400, -200, -4000), 600, Color.GRAY,
                0.1, 0.8, 0.2, 10., 0));

    }

    private double sq(double v) {
        return v * v;
    }

    public double intersection(Point e, Point p, Sphere s) {
        Point c = s.center;
        Vector v = p.minus(e);
        double A = v.sq();
        Vector ec = e.minus(c);
        double B = 2 * v.dp(ec);
        double C = ec.sq() - sq(s.radius);
        double delta = sq(B) - 4 * (A * C);
        if (delta < 0) return 0;
        if (delta == 0) {
            double lambda = -B / (2 * A);
            return lambda;
        }
        double lambda1 = (-B + Math.sqrt(delta)) / (2 * A);
        double lambda2 = (-B - Math.sqrt(delta)) / (2 * A);
        double lambda = (lambda1 < lambda2) ? lambda1 : lambda2;
        return lambda;
    }

    private double lambert(double coef, Vector normal, Vector light) {
        Vector toLight = light.negate();
        double value = coef * normal.dp(toLight);
        if (value < 0) return 0;
        if (value > 1) value = 1;
        return value;
    }

    private Color toColor(double ambientEnergy, Color ambientColor,
                          double lambertEnergy, Color object, Color light,
                          double phongEnergy, double mirrorEnergy, Color mirror) {
        double ambientR = ambientColor.getRed() / 255.;
        double ambientG = ambientColor.getGreen() / 255.;
        double ambientB = ambientColor.getBlue() / 255.;
        double baseR = object.getRed() / 255.;
        double baseG = object.getGreen() / 255.;
        double baseB = object.getBlue() / 255.;
        double lightR = light.getRed() / 255.;
        double lightG = light.getGreen() / 255.;
        double lightB = light.getBlue() / 255.;
        double mirrorR = mirror.getRed() / 255.;
        double mirrorG = mirror.getGreen() / 255.;
        double mirrorB = mirror.getBlue() / 255.;
        double r = ambientEnergy * ambientR +
                lambertEnergy * baseR * lightR +
                phongEnergy * lightR +
                mirrorEnergy * mirrorR;
        double g = ambientEnergy * ambientG +
                lambertEnergy * baseG * lightG +
                phongEnergy * lightG +
                mirrorEnergy * mirrorG;
        double b = ambientEnergy * ambientB +
                lambertEnergy * baseB * lightB +
                phongEnergy * lightB +
                mirrorEnergy * mirrorB;
        if (r > 1) r = 1;
        if (g > 1) g = 1;
        if (b > 1) b = 1;
        return new Color((float) (r), (float) (g), (float) (b));
    }

    private double phong(double coef1, double coef2, Vector normal, Vector light, Vector v) {
        Vector toEye = v.negate();
        toEye.normalize();
        Vector toLight = light.negate();
        toLight.normalize();
        Vector lightReflect = toLight.negate().plus(normal.multiply(2 * normal.dp(toLight)));
        lightReflect.normalize();
        double value = toEye.dp(lightReflect);
        if (value < 0) return 0;
        if (value > 1) value = 1;
        value = coef1 * Math.pow(value, coef2) * normal.dp(toLight);
        if (value < 0) return 0;
        if (value > 1) value = 1;
        return value;
    }

    public Color raytrace(int level, Point eye, Point onScreen) {
        double lambda = Double.POSITIVE_INFINITY;
        Sphere hitSphere = null;
        for (Sphere s : objects) {
            double l = intersection(eye, onScreen, s);
            if (l > 0 && l < lambda) {
                lambda = l;
                hitSphere = s;
            }
        }
        if (lambda == Double.POSITIVE_INFINITY) {
            return Color.BLACK;
        } else {
            Vector view = onScreen.minus(eye);
            Point hit = eye.translate(view.multiply(lambda));
            Vector normal = hit.minus(hitSphere.center);
            normal.normalize();

            Color rec = Color.WHITE;
            double mirror = 0;
            if (level != 0) {
                Vector v = view.negate();
                Vector vR = v.negate().plus(normal.multiply(normal.dp(v) * 2));
                vR.normalize();
                Point to = hit.translate(vR.multiply(5));
                rec = raytrace(level - 1, hit, to);
                mirror = hitSphere.mirror;
            }

            double ambientEnergy = hitSphere.ambient;
            double lambertEnergy = lambert(hitSphere.lambert, normal, light);
            double phongEnergy = phong(hitSphere.specular, hitSphere.specularPower, normal, light, view);
            Color c = toColor(ambientEnergy, ambientColor,
                    lambertEnergy, hitSphere.color, lightColor,
                    phongEnergy, mirror, rec);
            return c;
        }
    }

    public void run() {
        int w = panel.getPreferredSize().width;
        int h = panel.getPreferredSize().height;
        Point eye = new Point(0, 0, 0);
        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                double x = xx - w / 2;
                double y = yy - h / 2;
                Point onScreen = new Point(x, y, -1000);
                Color c = raytrace(3, eye, onScreen);
                graphics.setColor(c);
                ;
                graphics.drawLine(xx, h - yy, xx, h - yy);
                panel.repaint();
            }
        }
    }

    public void set(List<Sphere> objects) {
        this.objects = objects;
    }
}
