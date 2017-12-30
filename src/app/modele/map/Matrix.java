package app.modele.map;

import java.util.Arrays;

public class Matrix {

    private int[][] arr;
    private int size;
    private int min = 0;
    private int max = 0;

    public Matrix(int size) {
        this.size = size;
        arr = new int[size][size];
    }

    public int size() {
        return size;
    }

    public int get(int i, int j) {
        return arr[i][j];
    }

    public int[] get(int i) {
        return arr[i];
    }

    public void set(int i, int j, int value) {
        if (value > max)
            max = value;
        else if (value < min)
            min = value;
        arr[i][j] = value;
    }

    public void print() {
        for (int[] row : arr)
            System.out.println(Arrays.toString(row));
    }

    @Override
    public String toString() {
        return Arrays.deepToString(arr);
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
