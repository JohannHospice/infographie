package faceculling;

import faceculling.MyMath.Point;

import java.awt.*;

public class Pyramid extends WorldObject {
    public Pyramid(int baseWidth, int height) {
        // Points
        add(new Point(-baseWidth / 2, -baseWidth / 2, 0));
        add(new Point(-baseWidth / 2, baseWidth / 2, 0));
        add(new Point(baseWidth / 2, baseWidth / 2, 0));
        add(new Point(baseWidth / 2, -baseWidth / 2, 0));
        add(new Point(0, 0, height));
        // Faces
        add(Color.LIGHT_GRAY, 3, 2, 1, 0);
        add(Color.BLUE, 4, 0, 1);
        add(Color.GREEN, 4, 2, 3);
        add(Color.RED, 3, 0, 4);
        add(Color.CYAN, 2, 4, 1);
    }
}
