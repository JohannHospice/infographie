package app.map;

public interface MapGenerator {
    float[][] algorithm();

    MapGenerator set(int width, int height, float variance);
}
