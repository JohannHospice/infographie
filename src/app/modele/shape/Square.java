package app.modele.shape;

import app.modele.math.Point;
import app.modele.math.Segment;

public class Square extends WorldObject {
    public Square(int size) {
        Point[] points = new Point[4];
        points[0] = new Point(-size / 2, -size / 2, -size / 2);
        points[1] = new Point(-size / 2, size / 2, -size / 2);
        points[2] = new Point(size / 2, size / 2, -size / 2);
        points[3] = new Point(size / 2, -size / 2, -size / 2);
        for (int i = 0; i < 4; i++) {
            add(new Segment(points[i], points[(i + 1) % 4]));
        }
    }
}
