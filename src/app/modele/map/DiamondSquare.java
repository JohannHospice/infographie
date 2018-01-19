package app.modele.map;

import java.util.Arrays;

public class DiamondSquare implements MapGenerator {

    private int gensize;
    private int width;
    private int height;
    private float variance;

    public DiamondSquare() {
        gensize = (int) Math.pow(2, 9) + 1;
        width = gensize;
        height = gensize;
        variance = 1;
    }

    public DiamondSquare(int width, int height, float variance) {
        setSize(width, height);
        setVariance(variance);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;

        // gensize must be in the form 2^n + 1 and
        // also be greater or equal to both the width
        // and height
        float w = (float) Math.ceil(Math.log(width) / Math.log(2));
        float h = (float) Math.ceil(Math.log(height) / Math.log(2));
        gensize = w > h ? (int) Math.pow(2, w) + 1 : (int) Math.pow(2, h) + 1;
    }

    public void setGenerationSize(int n) {
        gensize = (int) Math.pow(2, n) + 1;
        width = gensize;
        height = gensize;
    }

    public void setVariance(float v) {
        variance = v;
    }

    public float[][] algorithm() {
        float[][] map = new float[gensize][gensize];

        // Place initial seeds for corners
        map[0][0] = (float) Math.random();
        map[0][map.length - 1] = (float) Math.random();
        map[map.length - 1][0] = (float) Math.random();
        map[map.length - 1][map.length - 1] = (float) Math.random();

        map = generate(map);

        if (width < gensize || height < gensize) {
            float[][] temp = new float[width][height];
            for (int i = 0; i < temp.length; i++)
                temp[i] = Arrays.copyOf(map[i], temp[i].length);
            map = temp;

        }

        return map;

    }

    @Override
    public MapGenerator set(int width, int height, float variance) {
        setSize(width, height);
        setVariance(variance);
        return this;
    }

    public float[][] generate(float[][] map) {

        map = map.clone();
        int step = map.length - 1;

        float v = variance;

        while (step > 1) {

            // SQUARE STEP
            for (int i = 0; i < map.length - 1; i += step) {
                for (int j = 0; j < map[i].length - 1; j += step) {
                    float average = (map[i][j] + map[i + step][j] + map[i][j + step] + map[i + step][j + step]) / 4;
                    if (map[i + step / 2][j + step / 2] == 0) // check if not pre-seeded
                        map[i + step / 2][j + step / 2] = average + randVariance(v);
                }
            }

            // DIAMOND STEP
            for (int i = 0; i < map.length - 1; i += step) {
                for (int j = 0; j < map[i].length - 1; j += step) {
                    if (map[i + step / 2][j] == 0) // check if not pre-seeded
                        map[i + step / 2][j] = averageDiamond(map, i + step / 2, j, step) + randVariance(v);
                    if (map[i][j + step / 2] == 0)
                        map[i][j + step / 2] = averageDiamond(map, i, j + step / 2, step) + randVariance(v);
                    if (map[i + step][j + step / 2] == 0)
                        map[i + step][j + step / 2] = averageDiamond(map, i + step, j + step / 2, step) + randVariance(v);
                    if (map[i + step / 2][j + step] == 0)
                        map[i + step / 2][j + step] = averageDiamond(map, i + step / 2, j + step, step) + randVariance(v);
                }
            }
            v /= 2;
            step /= 2;
        }
        return map;
    }

    private float averageDiamond(float[][] map, int x, int y, int step) {
        int count = 0;
        float average = 0;
        if (x - step / 2 >= 0) {
            count++;
            average += map[x - step / 2][y];
        }
        if (x + step / 2 < map.length) {
            count++;
            average += map[x + step / 2][y];
        }
        if (y - step / 2 >= 0) {
            count++;
            average += map[x][y - step / 2];
        }
        if (y + step / 2 < map.length) {
            count++;
            average += map[x][y + step / 2];
        }
        return average / count;
    }

    private float randVariance(float v) {
        return (float) (Math.random() * 2 * v - v);
    }
}
