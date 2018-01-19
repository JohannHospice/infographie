package app.modele.shape;

import app.modele.math.Point;
import app.modele.math.VectorXY;

import java.awt.*;
import java.util.LinkedList;

public class Map extends WorldObject {
    private final static Color color = Color.RED;

    private final int midSize, size;

    public Map(float[][] arr, int weight, LinkedList<VectorXY> visit) {
        this(arr, weight);

        int stroke = 8;
        float height = 10;

        for (VectorXY v : visit) {
            add(new Point(v.x * weight - midSize + stroke / 2, v.y * weight - midSize + stroke / 2, arr[v.x][v.y] + height));
            add(new Point(v.x * weight - midSize - stroke / 2, v.y * weight - midSize - stroke / 2, arr[v.x][v.y] + height));
        }

        for (int i = arr.length * arr[0].length; i < arr.length * arr[0].length + visit.size() * 2 - 4; i++)
            add(Color.BLUE, i, i + 1, i + 3, i + 2);
    }

    public Map(float arr[][], int weight) {
        size = arr.length;
        midSize = size * weight / 2;

        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr[i].length; j++)
                add(new Point(i * weight - midSize, j * weight - midSize, arr[i][j] ));

        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr[i].length - 1; j++) {
                add(color, index(i, j + 1, size), index(i + 1, j, size), index(i, j, size));
                add(color, index(i + 1, j + 1, size), index(i + 1, j, size), index(i, j + 1, size));
            }
    }

    private int index(int i, int j, int size) {
        return i * size + j;
    }
}
