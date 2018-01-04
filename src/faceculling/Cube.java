package faceculling;

import faceculling.MyMath.Point;

import java.awt.*;

public class Cube extends WorldObject {
    public Cube(double size) {
        // Points
        add(new Point(-size / 2, -size / 2, -size / 2));
        add(new Point(-size / 2, size / 2, -size / 2));
        add(new Point(size / 2, size / 2, -size / 2));
        add(new Point(size / 2, -size / 2, -size / 2));
        add(new Point(-size / 2, -size / 2, size / 2));
        add(new Point(-size / 2, size / 2, size / 2));
        add(new Point(size / 2, size / 2, size / 2));
        add(new Point(size / 2, -size / 2, size / 2));
        // Faces
        add(Color.RED, 3, 2, 1, 0);
        add(Color.BLUE, 4, 5, 6, 7);
        add(Color.GREEN, 7, 6, 2, 3);
        add(Color.CYAN, 4, 0, 1, 5);
        add(Color.MAGENTA, 1, 2, 6, 5);
        add(Color.ORANGE, 4, 7, 3, 0);
    }
}
