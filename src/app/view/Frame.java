package app.view;

import app.controller.FrameKeyControler;
import app.modele.map.DiamondSquare;
import app.modele.map.MatrixGenerator;
import app.view.panel.PlanPanel;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private static final String TITLE = "Map generator";
    private PlanPanel planPanel;
    private MatrixGenerator mapGen;
    public static int DEFAULT_SIZE = 1000;

    public Frame(MatrixGenerator mapGen) {
        super(TITLE);
        this.mapGen = mapGen;
        createUI();
    }

    private void createUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new FrameKeyControler(this));

        planPanel = new PlanPanel(DEFAULT_SIZE, DEFAULT_SIZE);
        getContentPane().add(planPanel);
        pack();
    }

    public void generate() {
        planPanel.setMatrix(mapGen.algorithm());
        repaint();
    }

    public void randomizeColor() {
        planPanel.randomizeColor();
        repaint();
    }

    public MatrixGenerator getMapGen() {
        return mapGen;
    }

    public static void main(String args[]) {
        final MatrixGenerator mapGen = new DiamondSquare(7);
        new Thread(() -> EventQueue.invokeLater(() -> {
            Frame frame = new Frame(mapGen);
            frame.generate();
            frame.setVisible(true);
        })).start();
    }
}
