package app.view;

import app.controller.WorldFrameKeyController;
import app.modele.math.*;
import app.modele.math.Point;
import app.modele.shape.WorldObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class WorldObjectPanel extends JPanel {
    private Matrix camera = Matrix.createPerspectiveZ(400);
    private Dimension dimension;
    private Vector vectorOfLight;
    private ArrayList<WorldObject> objects;

    public WorldObjectPanel(int w, int h, int sizeObjects) {
        dimension = new Dimension(w, h);
        vectorOfLight = new Vector(1, 10, 10);
        setLayout(new FlowLayout());
        objects = new ArrayList<>(sizeObjects + 1);
    }

    public Vector getLight() {
        return vectorOfLight;
    }

    public void setLight(Vector v) {
        vectorOfLight = v;
    }

    public void setWorldObject(int i, WorldObject transformedObject) {
        objects.set(i, transformedObject);
        repaint();
    }

    public int addWorldObject(WorldObject transformedObject) {
        objects.add(transformedObject);
        repaint();
        return objects.size() - 1;
    }

    @Override
    public Dimension getPreferredSize() {
        return dimension;
    }

    static int i = 100;

    @Override
    public void paintComponent(Graphics g) {
        i++;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, dimension.width, dimension.height);
        g.setColor(Color.BLACK);


        // camera = Calculus.multiply(Matrix.createPerspectiveZ(400), Matrix.createTranslation(new Vector(0,0,200)));
        for (WorldObject currentObject : objects) {
            for (Face f : currentObject) {
                Vector n = Calculus.normalize(Calculus.crossProduct(f.getVector(0), f.getVector(1)));

                Point pf = f.getPoint(0);
                Vector vz = new Vector(pf.getX(), pf.getY() + 80, pf.getZ()); // Perspective
                if (Calculus.dotProduct(n, vz) <= 0)
                    continue;

                Iterator<Point> ip = f.pointIterator();
                Polygon polygon = new Polygon();
                while (ip.hasNext()) {
                    Point p = Calculus.multiply(camera, ip.next());
                    if (p.getT() != 0) p.homogenize();
                    polygon.addPoint((int) (p.getX()), (int) (p.getY()));
                }

                polygon.translate((int) (getWidth() / 2), (int) (getHeight() / 2));
                Color c = f.getColor();
                if (c != null) {
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
    }

    public Matrix getCamera() {
        return camera;
    }

    public void setCamera(Matrix camera) {
        this.camera = camera;
    }
}
