package app.modele.shape;

import app.modele.math.Calculus;
import app.modele.math.Face;
import app.modele.math.Matrix;
import app.modele.math.Point;

import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

public class WorldObject implements Iterable<Face> {
    private Vector<Point> points;
    private Vector<Face> faces;
    private Matrix transform;

    protected WorldObject() {
        points = new Vector<>();
        faces = new Vector<>();
    }

    protected void add(Face f) {
        faces.add(f);
    }

    protected void add(Point p) {
        points.add(p);
    }

    protected void add(int... ps) {
        faces.add(new Face(ps, points));
    }

    protected void add(Color c, int... ps) {
        faces.add(new Face(ps, points, c));
    }

    protected Point get(int idx) {
        return points.get(idx);
    }

    public final void addTransform(Matrix m) {
        transform = app.modele.math.Calculus.multiply(m, transform);
    }

    public final void resetTransform() {
        transform = app.modele.math.Matrix.unity();
    }

    public WorldObject getTransformedObject() {
        WorldObject o = new WorldObject();
        for (Point p : points)
            o.add(Calculus.multiply(transform, p));
        for (Face f : this)
            o.add(new Face(f.getIndices(), o.points, f.getColor()));
        return o;
    }

    public Matrix getTransform() {
        return transform;
    }

    @Override
    public final Iterator<Face> iterator() {
        return faces.iterator();
    }

}
