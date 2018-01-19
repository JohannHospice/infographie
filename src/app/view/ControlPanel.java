package app.view;

import app.modele.math.Vector;
import app.modele.path.PathFinder;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private final WorldFrame worldFrame;

    public ControlPanel(WorldFrame worldFrame) {
        this.worldFrame = worldFrame;
        JPanel pc = new JPanel(new GridBagLayout());
        BoxLayout pcLayout = new BoxLayout(pc, BoxLayout.PAGE_AXIS);

        pc.setLayout(pcLayout);
        pc.add(generateViewPanel());

        pc.add(new JSeparator(SwingConstants.HORIZONTAL));
        pc.add(generateAlgoPanel());
        pc.add(new JSeparator(SwingConstants.HORIZONTAL));
        pc.add(generateLightPanel());
        pc.add(new JSeparator(SwingConstants.HORIZONTAL));
        pc.add(generatePathPanel());

        add(pc);
    }

    private JPanel generateViewPanel() {

        JButton down = new JButton("v");
        down.addActionListener(actionEvent -> worldFrame.incObjZoom());
        JButton up = new JButton("^");
        up.addActionListener(actionEvent -> worldFrame.decObjZoom());

        JButton zoomP = new JButton("+");
        zoomP.addActionListener(actionEvent -> worldFrame.incObjRotation());
        JButton zoomL = new JButton("-");
        zoomL.addActionListener(actionEvent -> worldFrame.decObjRotation());

        JPanel panel2 = new JPanel(new BorderLayout());
        JPanel panel1 = new JPanel(new BorderLayout());

        panel1.add(up, BorderLayout.NORTH);
        panel1.add(down, BorderLayout.SOUTH);
        panel2.add(zoomP, BorderLayout.NORTH);
        panel2.add(zoomL, BorderLayout.SOUTH);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("view"), BorderLayout.NORTH);
        JPanel subPanel = new JPanel();
        subPanel.add(panel1);
        subPanel.add(panel2);
        panel.add(subPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel generateLightPanel() {
        final Vector light = worldFrame.getLight();
        JPanel lightPnl = new JPanel();
        lightPnl.setLayout(new BoxLayout(lightPnl, BoxLayout.PAGE_AXIS));

        lightPnl.add(new JLabel("light"));
        JTextField m[] = new JTextField[]{
                new JTextField(String.valueOf(light.getX()), 1),
                new JTextField(String.valueOf(light.getY()), 1),
                new JTextField(String.valueOf(light.getZ()), 1)};
        String lightS[] = new String[]{"x:", "y:", "z:"};
        JPanel inputPnl = new JPanel(new GridLayout(lightS.length, 2));
        for (int i = 0; i < 3; i++) {
            inputPnl.add(new JLabel(lightS[i]));
            inputPnl.add(m[i]);
        }
        lightPnl.add(inputPnl);

        JButton button = new JButton("update light");
        button.addActionListener(actionEvent -> worldFrame.setLight(new Vector(Double.parseDouble(m[0].getText()), Double.parseDouble(m[1].getText()), Double.parseDouble(m[2].getText()))));
        lightPnl.add(button);
        return lightPnl;
    }


    private JPanel generateAlgoPanel() {
        JPanel pc = new JPanel();
        pc.setLayout(new BoxLayout(pc, BoxLayout.Y_AXIS));

        pc.add(new JLabel("map generation"));

        JPanel algoSwitcherPanel = new JPanel();
        algoSwitcherPanel.setLayout(new BoxLayout(algoSwitcherPanel, BoxLayout.PAGE_AXIS));

        algoSwitcherPanel.add(new JLabel("algorithm:"));

        JRadioButton diamondSquareBtn = new JRadioButton("diamond square");
        diamondSquareBtn.addActionListener(e -> worldFrame.setMapGenIndex(0));
        diamondSquareBtn.setSelected(true);

        JRadioButton perlinNoiseBtn = new JRadioButton("perlin noise");
        perlinNoiseBtn.addActionListener(e -> worldFrame.setMapGenIndex(1));

        ButtonGroup algoGroup = new ButtonGroup();
        algoGroup.add(diamondSquareBtn);
        algoGroup.add(perlinNoiseBtn);

        algoSwitcherPanel.add(perlinNoiseBtn);
        algoSwitcherPanel.add(diamondSquareBtn);
        pc.add(algoSwitcherPanel);

        // map gen
        JTextField q[] = new JTextField[]{
                new JTextField(String.valueOf(worldFrame.getMapSize()), 1),
                new JTextField(String.valueOf(worldFrame.getVariance()), 1),
                new JTextField(String.valueOf(worldFrame.getWeight()), 1)};
        String s[] = new String[]{"size:", "variance:", "weight:"};
        int numPairs = s.length;

        JPanel inputPnl = new JPanel(new GridLayout(numPairs, 2));
        for (int i = 0; i < numPairs; i++) {
            inputPnl.add(new JLabel(s[i]));
            inputPnl.add(q[i]);
        }
        pc.add(inputPnl);

        Button refresh = new Button("update map");
        refresh.addActionListener(e -> worldFrame.generateMap(Integer.parseInt(q[0].getText()), Integer.parseInt(q[2].getText()), Float.parseFloat(q[1].getText())));
        pc.add(refresh);
        return pc;
    }

    private JPanel generatePathPanel() {
        //path
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(new JLabel("pathfinder"), BorderLayout.NORTH);

        PathFinder pathFinder = worldFrame.getPathFinder();
        JTextField p[] = new JTextField[]{
                new JTextField(),
                new JTextField(),
                new JTextField(),
                new JTextField(),
                new JTextField(String.valueOf(pathFinder.getHeightMin())),
                new JTextField(String.valueOf(pathFinder.getHeightMax())),
                new JTextField(String.valueOf(pathFinder.getWeightMax()))};
        String ms[] = new String[]{"source x:", "source y:", "destination x:", "destination y:", "height min:", "height max:", "weight max:"};
        JPanel inputs = new JPanel(new GridLayout(4, 2));
        for (int i = 0; i < ms.length; i++) {
            inputs.add(new JLabel(ms[i]));
            inputs.add(p[i]);
        }
        panel.add(inputs, BorderLayout.CENTER);


        Button path = new Button("Path");
        panel.add(path, BorderLayout.SOUTH);
        path.addActionListener(actionEvent -> {
            pathFinder.setHeightMin(Integer.parseInt(p[4].getText())).setWeightMax(Integer.parseInt(p[5].getText())).setWeightMax(Integer.parseInt(p[6].getText()));
            worldFrame.generatePath(Integer.parseInt(p[0].getText()), Integer.parseInt(p[1].getText()), Integer.parseInt(p[2].getText()), Integer.parseInt(p[3].getText()));
        });
        return panel;

    }
}
