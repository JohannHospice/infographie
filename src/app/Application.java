package app;

import app.layout.SpringUtilities;
import app.map.DiamondSquare;
import app.map.MapGenerator;
import app.map.PerlinNoise;
import app.math.Calculus;
import app.math.Matrix;
import app.math.Vector;
import app.path.PathFinder;
import app.shape.Map;
import app.shape.WorldObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Application {
    private static final double FPS = 60;
    private static final int WINDOWS_HEIGHT = 1000;

    private static float variance = 120;
    private static int size = 125, height = 6;

    private static ArrayList<MapGenerator> mapGen = new ArrayList<>();
    private static int mapGenIndex = 0;
    private static WorldObject obj;

    static {
        mapGen.add(new DiamondSquare(size, size, variance));
        mapGen.add(new PerlinNoise(size, size, variance));
        float[][] grid = mapGen.get(mapGenIndex).algorithm();

        PathFinder pathFinder = new PathFinder(grid, 100);
        obj = new Map(grid, height, pathFinder.find(0, 0, size - 1, size - 1));
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
                new JTextField(String.valueOf(size), 1),
                new JTextField(String.valueOf(variance), 1),
                new JTextField(String.valueOf(height), 1)};
        String s[] = new String[]{"size:", "variance:", "weight:"};
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
        refresh.addActionListener(e -> {
            float[][] grid = mapGen.get(mapGenIndex).set(
                    Integer.parseInt(q[0].getText()),
                    Integer.parseInt(q[0].getText()),
                    Float.parseFloat(q[1].getText())).algorithm();

            PathFinder pathFinder = new PathFinder(grid, 100);
            obj = new Map(grid, Integer.parseInt(q[2].getText()), pathFinder.find(0, 0, size - 1, size - 1));
        });
        pc.add(refresh);


        //graphic panel
        mp = new WorldObjectPanel(WINDOWS_HEIGHT, WINDOWS_HEIGHT, 1);
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
            final Matrix camera = mp.getCamera();
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

                Matrix lt =Calculus.multiply(camera, Matrix.createTranslation(new Vector(0,0,-i))) ;

                mp.setCamera(lt);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::createUI);
        SwingUtilities.invokeLater(Application::start);
    }

}
