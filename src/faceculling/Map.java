package faceculling;

import faceculling.MyMath.Point;

import java.awt.*;

public class Map extends WorldObject {
    private final static Color color = Color.RED;

    public Map(float arr[][], int weight) {
        final int size = arr.length;
        final int midSize = size * weight / 2;

        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr[i].length; j++)
                add(new Point(i * weight - midSize, j * weight - midSize, arr[i][j]));

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
