package app.view;

import app.controller.WorldFrameKeyController;
import app.modele.map.DiamondSquare;
import app.modele.map.MapGenerator;
import app.modele.map.PerlinNoise;
import app.modele.math.Calculus;
import app.modele.math.Matrix;
import app.modele.math.Vector;
import app.modele.path.PathFinder;
import app.modele.shape.Map;
import app.modele.shape.WorldObject;
import app.view.layout.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public final class WorldFrame extends JFrame {
    private static final double FPS = 60;
    private static final int WINDOWS_HEIGHT = 1000;

    private float variance = 120;
    private int size = 125, height = 6;
    private int mapGenIndex = 0;
    private int weightMax = 20;

    private WorldObject obj;
    private WorldObjectPanel mp;

    private ArrayList<MapGenerator> mapGen = new ArrayList<>();

    public WorldFrame() {
        super("Map generator");
        init();

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        setContentPane(p);

        // p.add(buildControlPanel(), BorderLayout.WEST);

        mp = new WorldObjectPanel(WINDOWS_HEIGHT, WINDOWS_HEIGHT, 1);
        addKeyListener(new WorldFrameKeyController(mp));
        p.add(mp, BorderLayout.CENTER);

        pack();
    }

    //controller panel
    private JPanel buildControlPanel() {
        JPanel pc = new JPanel(new GridBagLayout());
        BoxLayout pcLayout = new BoxLayout(pc, BoxLayout.PAGE_AXIS);
        pc.setLayout(pcLayout);

        // algo
        JPanel algoPnl = new JPanel();
        algoPnl.setLayout(new BoxLayout(algoPnl, BoxLayout.PAGE_AXIS));

        algoPnl.add(new JLabel("algorithm:"));

        JRadioButton diamondSquareBtn = new JRadioButton("diamond square");
        diamondSquareBtn.addActionListener(e -> mapGenIndex = 0);
        diamondSquareBtn.setSelected(true);

        JRadioButton perlinNoiseBtn = new JRadioButton("perlin noise");
        perlinNoiseBtn.addActionListener(e -> mapGenIndex = 1);

        ButtonGroup algoGroup = new ButtonGroup();
        algoGroup.add(diamondSquareBtn);
        algoGroup.add(perlinNoiseBtn);

        algoPnl.add(perlinNoiseBtn);
        algoPnl.add(diamondSquareBtn);
        pc.add(algoPnl);

        // input
        JTextField q[] = new JTextField[]{
                new JTextField(String.valueOf(size), 1),
                new JTextField(String.valueOf(variance), 1),
                new JTextField(String.valueOf(height), 1)};
        String s[] = new String[]{"size:", "variance:", "weight:"};
        int numPairs = s.length;

        JPanel inputPnl = new JPanel(new SpringLayout());
        for (int i = 0; i < numPairs; i++) {
            inputPnl.add(new JLabel(s[i]));
            inputPnl.add(q[i]);
        }
        pc.add(inputPnl);
        SpringUtilities.makeCompactGrid(inputPnl, numPairs, 2, 6, 6, 6, 6);

        //refresh btn
        Button refresh = new Button("Refresh");
        refresh.addActionListener(e -> {
            float[][] grid = mapGen.get(mapGenIndex).set(
                    Integer.parseInt(q[0].getText()),
                    Integer.parseInt(q[0].getText()),
                    Float.parseFloat(q[1].getText())).algorithm();

            PathFinder pathFinder = new PathFinder(grid, weightMax);
            obj = new Map(grid, Integer.parseInt(q[2].getText()), pathFinder.find(0, 0, size - 1, size - 1));
        });
        pc.add(refresh);
        return pc;

    }

    private void init() {
        mapGen.add(new DiamondSquare(size, size, variance));
        mapGen.add(new PerlinNoise(size, size, variance));
        float[][] grid = mapGen.get(mapGenIndex).algorithm();

        PathFinder pathFinder = new PathFinder(grid, weightMax);
        obj = new Map(grid, height, pathFinder.find(0, 0, size - 1, size - 1));
    }

    public void start() {
        new Thread(() -> {
            float i = 0;
            final Vector light = new Vector(0, 0, 1);
            obj.resetTransform();
            mp.addWorldObject(obj.getTransformedObject());
            while (true) {
                obj.resetTransform();
                // obj.addTransform(Matrix.createRotationZ(Math.PI * 2 / 100 * i));
                obj.addTransform(Matrix.createRotationX(2));
                obj.addTransform(Matrix.createTranslation(new Vector(0, 0, 500)));

                // obj.addTransform(Matrix.createRotationX(Math.PI * 2 / 100 * i / 3));
                // obj.addTransform(Matrix.createRotationZ(Math.PI * 2 / 100 * i / 2));
                // Matrix lt = Matrix.createRotationY(i * -2 * Math.PI / 100);
                // Matrix lt2 = Matrix.createRotationX(i * -2 * Math.PI / 100 / 2);
                // Calculus.multiply(lt, light);

                mp.setWorldObject(0, obj.getTransformedObject());
                mp.setLight(light);
                mp.repaint();

                try {
                    Thread.sleep((long) (1 / FPS * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i += 1;
            }
        }).start();
    }

}
