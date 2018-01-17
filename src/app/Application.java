package app;

import app.map.DiamondSquare;
import app.map.MapGenerator;
import app.map.PerlinNoise;
import app.math.Matrix;
import app.math.Vector;
import app.path.PathFinder;
import app.shape.Map;
import app.layout.SpringUtilities;
import app.shape.WorldObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Application {
    private static final double FPS = 60;
    private static final int WINDOWS_HEIGHT = 1000;

    private static final float VARIANCE = 120;
    private static final int HEIGHT = 125, WIDTH = 125, WEIGHT = 6;

    private static ArrayList<MapGenerator> mapGen = new ArrayList<>();
    private static int mapGenIndex = 0;
    private static WorldObject obj;

    static {
        mapGen.add(new DiamondSquare(WIDTH, HEIGHT, VARIANCE));
        mapGen.add(new PerlinNoise(WIDTH, HEIGHT, VARIANCE));
        float[][] grid = mapGen.get(mapGenIndex).algorithm();

        PathFinder pathFinder = new PathFinder(grid, 100);
        obj = new Map(grid, WEIGHT, pathFinder.find(14, 10, 30, 80));
    }

    private static WorldObjectPanel mp;

    private static void createUI() {
        JFrame f = new JFrame("Map generator");
        JPanel p = new JPanel();
        f.setContentPane(p);
        p.setLayout(new BorderLayout());

        //controller panel
        Panel pc = new Panel(new GridBagLayout());
        BoxLayout pcLayout = new BoxLayout(pc, BoxLayout.PAGE_AXIS);
        pc.setLayout(pcLayout);
        p.add(pc, BorderLayout.WEST);

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
                new JTextField(String.valueOf(WIDTH), 1),
                new JTextField(String.valueOf(HEIGHT), 1),
                new JTextField(String.valueOf(VARIANCE), 1),
                new JTextField(String.valueOf(WEIGHT), 1)};
        String s[] = new String[]{"width: ", "height:", "variance:", "weight:"};
        int numPairs = s.length;

        JPanel inputPnl = new JPanel(new SpringLayout());
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(s[i], JLabel.TRAILING);
            p.add(l);
            l.setLabelFor(q[i]);
            p.add(q[i]);

            inputPnl.add(new JLabel(s[i]));
            inputPnl.add(q[i]);
        }
        pc.add(inputPnl);
        SpringUtilities.makeCompactGrid(inputPnl,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        //refresh btn
        Button refresh = new Button("Refresh");
        refresh.addActionListener(e -> obj = new Map(
                mapGen.get(mapGenIndex).set(
                        Integer.parseInt(q[0].getText()),
                        Integer.parseInt(q[1].getText()),
                        Float.parseFloat(q[2].getText())).algorithm(),
                Integer.parseInt(q[3].getText())));
        pc.add(refresh);


        //graphic panel
        mp = new WorldObjectPanel(WINDOWS_HEIGHT, WINDOWS_HEIGHT);
        p.add(mp, BorderLayout.CENTER);

        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // f.addKeyListener(new WorldFrameKeyController(obj, mp));
    }

    private static void start() {
        new Thread(() -> {
            float i = 0;
            final Vector light = new Vector(0, 0, 1);
            while (true) {
                obj.resetTransform();
                obj.addTransform(Matrix.createRotationZ(Math.PI * 2 / 100 * i));
                obj.addTransform(Matrix.createRotationX(2));
                // obj.addTransform(Matrix.createRotationX(Math.PI * 2 / 100 * i / 3));
                // obj.addTransform(Matrix.createRotationZ(Math.PI * 2 / 100 * i / 2));
                obj.addTransform(Matrix.createTranslation(new Vector(0, 0, 500)));
                // Matrix lt = Matrix.createRotationY(i * -2 * Math.PI / 100);
                // Matrix lt2 = Matrix.createRotationX(i * -2 * Math.PI / 100 / 2);
                // Calculus.multiply(lt, light));
                mp.setWorldObject(obj.getTransformedObject(), light);
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

    public static void main(String[] args) {
        Application.createUI();
        SwingUtilities.invokeLater(Application::start);
    }

}
