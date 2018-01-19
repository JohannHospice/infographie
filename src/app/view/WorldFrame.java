package app.view;

import app.controller.WorldFrameKeyController;
import app.modele.map.DiamondSquare;
import app.modele.map.MapGenerator;
import app.modele.map.PerlinNoise;
import app.modele.math.Matrix;
import app.modele.math.Vector;
import app.modele.math.VectorXY;
import app.modele.path.PathFinder;
import app.modele.shape.Map;
import app.modele.shape.WorldObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public final class WorldFrame extends JFrame {
    private static final double FPS = 60;
    private static final int WINDOWS_HEIGHT = 1000;
    private final WorldObjectPanel mp;
    private final ArrayList<MapGenerator> mapGen = new ArrayList<>();
    private final PathFinder pathFinder = new PathFinder();
    private float variance = 100;
    private int size = 120, weight = 6, mapGenIndex = 0, pathfinderHeightMax = 200;
    private WorldObject obj;
    private Vector light = new Vector(0, 0, 1);
    private Vector objPosition = new Vector(0, 0, 500);
    private double spZoom = 10;
    private double zRotation = 1;
    private float[][] grid;

    public WorldFrame() {
        super("Map generator");
        init();

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        setContentPane(p);

        p.add(new ControlPanel(this), BorderLayout.WEST);

        mp = new WorldObjectPanel(WINDOWS_HEIGHT, WINDOWS_HEIGHT, 1);
        addKeyListener(new WorldFrameKeyController(mp));
        p.add(mp, BorderLayout.CENTER);

        pack();
    }

    public int getMapSize() {
        return size;
    }

    public void generateMap(int size, int weight, float variance) {
        this.size = size;
        this.weight = weight;
        this.variance = variance;
        grid = mapGen.get(mapGenIndex).set(size, size, variance).algorithm();
        obj = new Map(grid, weight);
    }

    public void generatePath(int srcX, int srxY, int dstX, int dstY) {
        LinkedList<VectorXY> path = pathFinder.setMap(grid).find(srcX, srxY, dstX, dstY); //.setWeightMax(weight).setHeightMax(pathfinderHeightMax).setHeightMin(0);
        obj = new Map(grid, weight, path);
    }

    private void init() {
        mapGen.add(new DiamondSquare(size, size, variance));
        mapGen.add(new PerlinNoise(size, size, variance));
        generateMap(size, weight, variance);
    }

    public void start() {
        new Thread(() -> {
            float i = 0;

            while (true) {
                obj.resetTransform();
                obj.addTransform(Matrix.createRotationY(Math.PI * 2 / 100 * zRotation));
                obj.addTransform(Matrix.createRotationZ(Math.PI * 2 / 100 * i));
                obj.addTransform(Matrix.createRotationX(2));
                obj.addTransform(Matrix.createTranslation(objPosition));
                mp.setWorldObject(obj.getTransformedObject());

                mp.setLight(light);
                mp.repaint();

                try {
                    Thread.sleep((long) (1 / FPS * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i += .1;
            }
        }).start();
    }

    public Vector getLight() {
        return light;
    }

    public void setLight(Vector light) {
        this.light = light;
    }

    public void setMapGenIndex(int mapGenIndex) {
        this.mapGenIndex = mapGenIndex;
    }

    public float getVariance() {
        return variance;
    }

    public int getWeight() {
        return weight;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public void incObjRotation() {
        zRotation += spZoom;
    }

    public void decObjRotation() {
        zRotation -= spZoom;
    }

    public void incObjZoom() {
        objPosition = new Vector(0, 0, objPosition.getZ() + spZoom);
    }

    public void decObjZoom() {
        objPosition = new Vector(0, 0, objPosition.getZ() - spZoom);
    }
}
