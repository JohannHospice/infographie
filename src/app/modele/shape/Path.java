package app.modele.shape;

import app.modele.math.Point;
import app.modele.math.VectorXYZ;

import java.awt.*;
import java.util.LinkedList;

public class Path extends WorldObject {
    private final static Color color = Color.GREEN;

    public Path(LinkedList<VectorXYZ> a, int weight, int stroke) {
        for (VectorXYZ v : a) {
            add(new Point(v.x * weight + stroke / 2, v.y * weight + stroke / 2, v.z + stroke / 2));
            add(new Point(v.x * weight - stroke / 2, v.y * weight - stroke / 2, v.z - stroke / 2));
        }
        for (int i = 0; i < a.size() - 2; i += 2) {
            add(color, i, i + 1, i + 2, i + 3);
        }
    }
}
