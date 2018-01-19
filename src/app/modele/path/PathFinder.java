package app.modele.path;

import app.modele.math.VectorXY;

import java.util.LinkedList;

public class PathFinder {

    private static final int[]
            DIR_X = {0, 0, 1, -1, 1, -1, 1, -1},
            DIR_Y = {1, -1, 0, 0, 1, 1, -1, -1};

    private float weightMax = Float.POSITIVE_INFINITY,
            heightMax = Float.POSITIVE_INFINITY,
            heightMin = Float.NEGATIVE_INFINITY;

    private float[][] map;
    private int n, m;

    public PathFinder() {
    }

    public LinkedList<VectorXY> find(int srcX, int srcY, int dstX, int dstY) {
        if (!(inBound(srcX, srcY) && inBound(dstX, dstY)) || map == null)
            return null;
        VectorXY[][] previous = bellmanford(srcX, srcY);
        return solve(srcX, srcY, dstX, dstY, previous);
    }

    private VectorXY[][] bellmanford(int srcX, int srcY) {
        float[][] distance = new float[n][m];
        VectorXY[][] previous = new VectorXY[n][m];

        // init
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                distance[i][j] = Float.POSITIVE_INFINITY;
                previous[i][j] = null;
            }
        distance[srcX][srcY] = 0;

        LinkedList<VectorXY> queue = new LinkedList<>();
        // float minHeight = Float.POSITIVE_INFINITY;
        for (int x = 0; x < n; x++)
            for (int y = 0; y < m; y++) {
                // if (minHeight > map[x][y]) minHeight = map[x][y];
                queue.add(new VectorXY(x, y));
            }

        while (!queue.isEmpty()) {
            VectorXY min = foundMin(distance, queue);
            queue.remove(min);

            int x = min.x;
            int y = min.y;
            for (int i = 0; i < DIR_X.length; i++) {
                int nextX = x + DIR_X[i], nextY = y + DIR_Y[i];
                if (inBound(nextX, nextY)) {

                    if (heightMin <= map[nextX][nextY] && map[nextX][nextY] <= heightMax) {
                        float weight = Math.abs(
                                map[x][y] - map[nextX][nextY]);
                        if (weight < weightMax)
                            if (distance[nextX][nextY] > distance[x][y] + weight) {
                                distance[nextX][nextY] = distance[x][y] + weight;
                                previous[nextX][nextY] = new VectorXY(x, y);
                            }
                    }
                }
            }
        }
        return previous;
    }

    private VectorXY foundMin(float[][] distance, LinkedList<VectorXY> queue) {
        VectorXY min = null;
        float minV = Float.POSITIVE_INFINITY;
        for (VectorXY v : queue) {
            if (minV > distance[v.x][v.y]) {
                minV = distance[v.x][v.y];
                min = v;
            }
        }
        return min;
    }

    private LinkedList<VectorXY> solve(int srcX, int srcY, int dstX, int dstY, VectorXY[][] previous) {
        LinkedList<VectorXY> path = new LinkedList<>();

        VectorXY vector = new VectorXY(dstX, dstY);
        while (vector.x != srcX && vector.y != srcY) {
            path.add(vector);
            vector = previous[vector.x][vector.y];
        }
        path.add(new VectorXY(vector.x, vector.y));

        return path;
    }

    boolean inBound(int x, int y) {
        return x >= 0 && y >= 0 && x < map.length && y < map[0].length;
    }

    public float[][] getMap() {
        return map;
    }

    public PathFinder setMap(float[][] map) {
        n = map.length;
        m = map[0].length;
        this.map = map;
        return this;
    }

    public float getHeightMin() {
        return heightMin;
    }

    public PathFinder setHeightMin(float heightMin) {
        this.heightMin = heightMin;
        return this;
    }

    public float getHeightMax() {
        return heightMax;
    }

    public PathFinder setHeightMax(float heightMax) {
        this.heightMax = heightMax;
        return this;
    }

    public float getWeightMax() {
        return weightMax;
    }

    public PathFinder setWeightMax(float weightMax) {
        this.weightMax = weightMax;
        return this;
    }

}
