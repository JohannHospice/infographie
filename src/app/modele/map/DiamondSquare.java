package app.modele.map;

import app.modele.math.Random;

public class DiamondSquare implements MatrixGenerator {
    private int size;
    private int minHeight, maxHeight;
    private int n;

    public DiamondSquare(int n) {
        setN(n);
    }

    @Override
    public Matrix algorithm() {
        Matrix matrix = new Matrix(size);
        initialisation(matrix);
        return propagate(matrix);
    }

    /**
     * initialisation des coins
     */
    private void initialisation(Matrix matrix) {
        matrix.set(0, 0, Random.next(-size, size));
        matrix.set(0, size - 1, Random.next(-size, size));
        matrix.set(size - 1, size - 1, Random.next(-size, size));
        matrix.set(size - 1, 0, Random.next(-size, size));
    }

    private Matrix propagate(Matrix matrix) {
        int i = size - 1;
        while (i > 1) {
            int id = i / 2;
            diamond(matrix, id, i);
            square(matrix, id, i);
            i = id;
        }
        return matrix;
    }

    /**
     * début de la phase du diamant
     */
    private void diamond(Matrix matrix, int id, int i) {
        for (int x = id; x < size; x += i) {
            for (int y = id; y < size; y += i) {
                int sum = matrix.get(x - id, y - id)
                        + matrix.get(x - id, y + id)
                        + matrix.get(x + id, y + id)
                        + matrix.get(x + id, y - id);
                int avg = sum / 4;
                matrix.set(x, y, avg + Random.next(-id, id));
            }
        }
    }

    /**
     * début de la phase du carré
     */
    private void square(Matrix matrix, int id, int i) {
        for (int x = 0; x < size; x += id) {
            int shift = x % i == 0 ? id : 0;
            for (int y = shift; y < size; y += i) {
                int sum = 0, num = 0;
                if (x >= id) {
                    sum = sum + matrix.get(x - id, y);
                    num = num + 1;
                }
                if (x + id < size) {
                    sum = sum + matrix.get(x + id, y);
                    num = num + 1;
                }
                if (y >= id) {
                    sum = sum + matrix.get(x, y - id);
                    num = num + 1;
                }
                if (y + id < size) {
                    sum = sum + matrix.get(x, y + id);
                    num = num + 1;
                }
                matrix.set(x, y, sum / num + Random.next(-id, id));
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
