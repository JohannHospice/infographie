package app.modele.map;

import app.modele.math.Random;

public class OldDiamondSquare implements MapGenerator {
    private int size, n;

    public OldDiamondSquare(int n) {
        setN(n);
    }

    @Override
    public float[][] algorithm() {
        float arr [][] = new float[size][size];
        initialisation(arr);
        return propagate(arr);
    }

    /**
     * initialisation des coins
     */
    private void initialisation(float arr[][]) {
        arr[0][0] = Random.next(-size, size);
        arr[0][size - 1] = Random.next(-size, size);
        arr[size - 1][size - 1] = Random.next(-size, size);
        arr[size - 1][0] = Random.next(-size, size);
    }

    private float[][] propagate(float arr[][]) {
        int i = size - 1;
        while (i > 1) {
            int id = i / 2;
            diamond(arr, id, i);
            square(arr, id, i);
            i = id;
        }
        return arr;
    }

    /**
     * début de la phase du diamant
     */
    private void diamond(float arr[][], int id, int i) {
        for (int x = id; x < size; x += i) {
            for (int y = id; y < size; y += i) {
                float sum = arr[x - id][y - id]
                        + arr[x - id][y + id]
                        + arr[x + id][y + id]
                        + arr[x + id][y - id];
                float avg = sum / 4;
                arr[x][y] = avg + Random.next(-id, id);
            }
        }
    }

    /**
     * début de la phase du carré
     */
    private void square(float arr[][], int id, int i) {
        for (int x = 0; x < size; x += id) {
            int shift = x % i == 0 ? id : 0;
            for (int y = shift; y < size; y += i) {
                float sum = 0, num = 0;
                if (x >= id) {
                    sum = sum + arr[x - id][ y];
                    num = num + 1;
                }
                if (x + id < size) {
                    sum = sum + arr[x + id][ y];
                    num = num + 1;
                }
                if (y >= id) {
                    sum = sum + arr[x][y - id];
                    num = num + 1;
                }
                if (y + id < size) {
                    sum = sum + arr[x][y + id];
                    num = num + 1;
                }
                arr[x][y]  = sum / num + Random.next(-id, id);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setN(int n) {
        this.n = n;
        size = (int) (Math.pow(2, n) + 1);
    }

    public int getN() {
        return n;
    }
}
