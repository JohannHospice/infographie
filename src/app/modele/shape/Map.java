package app.modele.shape;

import app.modele.math.Point;
import app.modele.math.Segment;

public class Map extends WorldObject {
    public Map(float arr[][], int weight) {
        final int size = arr.length;
        Point[][] points = new Point[size][size];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                points[i][j] = new Point(i * weight, j * weight, arr[i][j]);

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                add(new Segment(points[i][j], points[i + 1][j]));
                add(new Segment(points[i][j], points[i][j + 1]));
            }
        }
        for (int j = 0; j < size - 1; j++)
            add(new Segment(points[j][size - 1], points[j + 1][size - 1]));
    }
}
