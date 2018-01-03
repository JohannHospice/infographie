package app.view;

import app.controller.WorldFrameKeyController;
import app.modele.map.DiamondSquare;
import app.modele.shape.Map;
import app.modele.shape.WorldObject;
import app.view.panel.WorldPanel;

import javax.swing.*;
import java.awt.*;

public final class WorldFrame extends JFrame {
    private static final int DEFAULT_SIZE = 1000;
    private WorldPanel mp = new WorldPanel(DEFAULT_SIZE, DEFAULT_SIZE);

    private WorldFrame(final WorldObject obj) {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(mp, BorderLayout.CENTER);
        setContentPane(p);
        pack();
        addKeyListener(new WorldFrameKeyController(obj, mp));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WorldFrame::createUI);
    }

    private static void createUI() {
        DiamondSquare mapGenerator = new DiamondSquare();
        mapGenerator.setGenerationSize(7);
        mapGenerator.setVariance(250);
        WorldObject obj = new Map(mapGenerator.algorithm(), 10);

        WorldFrame worldFrame = new WorldFrame(obj);
        worldFrame.setVisible(true);
        worldFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
