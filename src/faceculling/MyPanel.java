package faceculling;

import faceculling.MyMath.*;
import faceculling.MyMath.Point;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class MyPanel extends JPanel {
    private boolean polygonOn;
    private boolean lightOn;
    private boolean colorOn;
    private boolean perspectiveOn;
    private Dimension dimension;
    private WorldObject object;
    private Vector vectorOfLight;

    public MyPanel(int w, int h) {
        dimension = new Dimension(w, h);
        vectorOfLight = new Vector(1, 10, 10);
        setLayout(new FlowLayout());
        polygonOn = false;
        lightOn = true;
        colorOn = true;
        perspectiveOn = true;

        JRadioButton b = new JRadioButton("Grid");
        b.setSelected(polygonOn);
        b.addActionListener(e -> polygonOn = ((JRadioButton) e.getSource()).isSelected());
        add(b);

        JRadioButton b2 = new JRadioButton("Light");
        b2.setSelected(lightOn);
        b2.addActionListener(e -> lightOn = ((JRadioButton) e.getSource()).isSelected());
        add(b2);

        JRadioButton b3 = new JRadioButton("Color");
        b3.setSelected(colorOn);
        b3.addActionListener(e -> colorOn = ((JRadioButton) e.getSource()).isSelected());
        add(b3);

        JRadioButton b4 = new JRadioButton("Perspective/Projection");
        b4.setSelected(perspectiveOn);
        b4.addActionListener(e -> perspectiveOn = ((JRadioButton) e.getSource()).isSelected());
        add(b4);
    }

    public Vector getLight() {
        return vectorOfLight;
    }

    public void setLight(Vector v) {
        vectorOfLight = v;
    }

    public void setWorldObject(WorldObject c, Vector light) {
        object = c;
        vectorOfLight = light;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return dimension;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, dimension.width, dimension.height);
        g.setColor(Color.BLACK);
        Matrix m;
        if (perspectiveOn)
            m = Matrix.createPerspectiveZ(400);
        else
            m = Matrix.createParallelZ();
        WorldObject currentObject = object;
        if (currentObject != null) {
            for (Face f : currentObject) {
                Vector v0 = f.getVector(0);
                Vector v1 = f.getVector(1);
                Vector n = Calculus.normalize(Calculus.crossProduct(v0, v1));
                Point pf = f.getPoint(0);
                Vector vz;
                if (perspectiveOn)
                    vz = new Vector(pf.getX(), pf.getY(), pf.getZ()); // Perspective
                else
                    vz = new Vector(0, 0, 1); // Parallel projection
                if (Calculus.dotProduct(n, vz) <= 0) {
                    continue;
                }
                Iterator<Point> ip = f.pointIterator();
                Polygon polygon = new Polygon();
                while (ip.hasNext()) {
                    Point p = Calculus.multiply(m, ip.next());
                    if (p.getT() != 0) p.homogenize();
                    polygon.addPoint((int) (p.getX()), (int) (p.getY()));
                }
                polygon.translate((int) (getWidth() / 2), (int) (getHeight() / 2));
                if (colorOn) {
                    Color c = f.getColor();
                    if (c != null) {
                        if (lightOn) {
                            Color infiniteLight = Color.WHITE;
                            Color ambient = Color.WHITE;
                            double iAmbientR = ambient.getRed() / 255.0;
                            double iAmbientG = ambient.getGreen() / 255.0;
                            double iAmbientB = ambient.getBlue() / 255.0;
                            double ambientR = 0.1 * c.getRed() / 255.0;
                            double ambientG = 0.1 * c.getGreen() / 255.0;
                            double ambientB = 0.1 * c.getBlue() / 255.0;
                            double iInfiniteR = infiniteLight.getRed() / 255.0;
                            double iInfiniteG = infiniteLight.getGreen() / 255.0;
                            double iInfiniteB = infiniteLight.getBlue() / 255.0;
                            double lambertianR = 0.6 * c.getRed() / 255.0;
                            double lambertianG = 0.6 * c.getGreen() / 255.0;
                            double lambertianB = 0.6 * c.getBlue() / 255.0;
                            double specularR = 0.9;
                            double specularG = 0.9;
                            double specularB = 0.9;
                            double coef = Calculus.dotProduct(Calculus.normalize(n), Calculus.normalize(vectorOfLight));
                            if (coef < 0) coef = 0;
                            double coefSpec = 0;
                            if (Calculus.dotProduct(vectorOfLight, n) >= 0)
                                coefSpec = Calculus.dotProduct(Calculus.normalize(Calculus.mirror(vectorOfLight, n)), Calculus.normalize(vz));
                            if (coefSpec < 0) {
                                coefSpec = 0;
                            } else {
                                coefSpec = coefSpec * coefSpec;
                                coefSpec = coefSpec * coefSpec;
                                coefSpec = coefSpec * coefSpec;
                                coefSpec = coefSpec * coefSpec;
                            }
                            double R = specularR * iInfiniteR * coefSpec + lambertianR * iInfiniteR * coef + ambientR * iAmbientR;
                            double G = specularG * iInfiniteG * coefSpec + lambertianG * iInfiniteG * coef + ambientG * iAmbientG;
                            double B = specularB * iInfiniteB * coefSpec + lambertianB * iInfiniteB * coef + ambientB * iAmbientB;
                            if (R < 0) R = 0;
                            else if (R > 1) R = 1;
                            if (G < 0) G = 0;
                            else if (G > 1) G = 1;
                            if (B < 0) B = 0;
                            else if (B > 1) B = 1;
                            Color col = new Color((float) R, (float) G, (float) B);
                            g.setColor(col);
                        } else {
                            g.setColor(c);
                        }
                        g.fillPolygon(polygon);
                    }
                }
                if (polygonOn) {
                    g.setColor(Color.BLACK);
                    g.drawPolygon(polygon);
                }
            }
        }
    }
}
