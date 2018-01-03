package faceculling;

import app.modele.map.DiamondSquare;
import faceculling.MyMath.Calculus;
import faceculling.MyMath.Matrix;
import faceculling.MyMath.Vector;

import javax.swing.*;
import java.awt.*;

public class FaceCulling {
    private static final double FPS = 60.0;
    //	private static WorldObject obj = new Cube(100);
    private static WorldObject obj;
    private static DiamondSquare mapGen = new DiamondSquare();

    static {
        mapGen.setVariance(150);
        mapGen.setGenerationSize(7);
        obj = new Map(mapGen.algorithm(), 10);
    }

    public static void createUI() {
        JFrame f = new JFrame("Face Culling and Illumination");
        JPanel p = new JPanel();
        f.setContentPane(p);
        p.setLayout(new BorderLayout());
        MyPanel mp = new MyPanel(500, 500);
        p.add(mp, BorderLayout.CENTER);

        JMenu m = new JMenu("Objects");

        JMenuItem cube = new JMenuItem("faceculling.Cube");
        cube.addActionListener(e -> obj = new Cube(100));
        m.add(cube);

        JMenuItem map = new JMenuItem("faceculling.Map");
        map.addActionListener(e -> obj = new Map(mapGen.algorithm(), 10));
        m.add(map);

        JMenuItem sphere = new JMenuItem("faceculling.raytracer.Sphere");
        sphere.addActionListener(e -> obj = new Sphere(40, 160, 200));
        m.add(sphere);

        JMenuItem pyramid = new JMenuItem("faceculling.Pyramid");
        pyramid.addActionListener(e -> obj = new Pyramid(100, 100));
        m.add(pyramid);

        JMenuBar mb = new JMenuBar();
        f.setJMenuBar(mb);
        mb.add(m);


        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // f.addKeyListener(new WorldFrameKeyController(obj, mp));

        new Thread(() -> {
            int i = 0;
            final Vector light = new Vector(0, 0, 1);
            while (true) {
                obj.resetTransform();
                obj.addTransform(Matrix.createRotationY(Math.PI * 2 / 100 * i));
                obj.addTransform(Matrix.createRotationX(Math.PI * 2 / 100 * i / 3));
                obj.addTransform(Matrix.createRotationZ(Math.PI * 2 / 100 * i / 2));
                obj.addTransform(Matrix.createTranslation(new Vector(0, 0, 500)));
                Matrix lt = Matrix.createRotationY(i * -2 * Math.PI / 100);
                // Matrix lt2 = Matrix.createRotationX(i * -2 * Math.PI / 100 / 2);
                Vector l = Calculus.multiply(lt, light);
                // l = Calculus.multiply(lt2, l);
                mp.setWorldObject(obj.getTransformedObject(), l);
                try {
                    Thread.sleep((int) (1 / FPS * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }).start();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FaceCulling::createUI);
    }

}
