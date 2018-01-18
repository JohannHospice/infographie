package app.modele.path;

import app.modele.math.VectorXY;
import app.modele.math.VectorXYZ;

import java.util.LinkedList;

public class PathFinder {

    private static final int[]
            DIR_X = {0, 0, 1, -1, 1, -1, 1, -1},
            DIR_Y = {1, -1, 0, 0, 1, 1, -1, -1};

    private float[][] map;
    private float weightMax;
    private int n, m;

    public PathFinder(float[][] map, float weightMax) {
        this.map = map;
        this.weightMax = weightMax;
        n = map.length;
        m = map[0].length;
    }

    /**
     * bellmanford
     */
    public LinkedList<VectorXYZ> find(int srcX, int srcY, int dstX, int dstY) {
        if (!(inBound(srcX, srcY) && inBound(dstX, dstY)))
            return null;

        float[][] distance = new float[n][m];
        VectorXY[][] previous = new VectorXY[n][m];

        // init
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                distance[i][j] = Float.POSITIVE_INFINITY;
                previous[i][j] = null;
            }
        distance[srcX][srcY] = 0;

        for (int j = 0; j < n; j++)

            for (int x = 0; x < n; x++)
                for (int y = 0; y < m; y++)

                    for (int i = 0; i < DIR_X.length; i++) { // chaques liens
                        int nextX = x + DIR_X[i], nextY = y + DIR_Y[i];
                        if (inBound(nextX, nextY)) {
                            float weight = Math.abs(map[x][y] - map[nextX][nextY]);
                            if (weight < weightMax)
                                if (distance[nextX][nextY] > distance[x][y] + weight) {
                                    distance[nextX][nextY] = distance[x][y] + weight;
                                    previous[nextX][nextY] = new VectorXY(x, y);
                                }
                        }
                    }

        LinkedList<VectorXYZ> path = new LinkedList<>();
        int x = dstX, y = dstY;
        int c = 0;
        while (x != srcX && y != srcY) {
            if (c > n * m)
                return path; //null
            c++;
            path.add(new VectorXYZ(x, y, map[x][y]));// real value in distance

            x = previous[x][y].x;
            y = previous[x][y].y;
        }
        path.add(new VectorXYZ(srcX, srcY, map[srcX][srcY]));// real value in distance
        return path;
    }

    boolean inBound(int x, int y) {
        return x >= 0 && y >= 0 && x < map.length && y < map[0].length;
    }

}
