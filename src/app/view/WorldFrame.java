package app.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import app.controller.WorldFrameKeyController;
import app.modele.map.DiamondSquare;
import app.modele.math.Matrix;
import app.modele.math.Vector;
import app.view.panel.WorldPanel;
import app.modele.shape.*;

public final class WorldFrame extends JFrame {
    private static final int DEFAULT_SIZE = 1000;
    private WorldPanel mp = new WorldPanel(DEFAULT_SIZE, DEFAULT_SIZE);
    private WorldObject obj;

    public WorldObject getObj() {
        return obj;
    }

    public WorldPanel getMp() {
        return mp;
    }

    private WorldFrame(final WorldObject obj) {
        this.obj = obj;
        addKeyListener(new WorldFrameKeyController(this));
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(mp, BorderLayout.CENTER);
        setContentPane(p);
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WorldFrame::createUI);
    }

    private static void createUI() {
        WorldObject obj = new Map(new DiamondSquare(7).algorithm(), 3);
        WorldFrame worldFrame = new WorldFrame(obj);
        worldFrame.setVisible(true);
    }

}
