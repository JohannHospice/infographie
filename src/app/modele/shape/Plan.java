package app.modele.shape;

import app.modele.math.Point;

import java.awt.*;

public class Plan extends WorldObject {
    public Plan(double x, double y, double z, double sizeX, double sizeY) {
        add(new Point(x, y, z));
        add(new Point(x + sizeX, y, z));
        add(new Point(x, y + sizeY, z));
        add(new Point(x + sizeX, y + sizeY, z));

        add(Color.CYAN, 0, 1, 3, 2);
    }
}
