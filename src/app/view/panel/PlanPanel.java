package app.view.panel;

import app.modele.map.Matrix;
import app.modele.math.Random;

import java.awt.*;

public class PlanPanel extends GraphicPanel {
    private Matrix matrix;
    private int[] colors = new int[3];
    private int colorVariable;

    public PlanPanel(int w, int h) {
        super(w, h);
    }

    public void randomizeColor() {
        int colorMax = Random.next(0, 255);
        int colorMin = Random.next(0, colorMax);
        for (int i = 0; i < colors.length; i++)
            colors[i] = Random.next(colorMin, colorMax);
        colorVariable = Random.next(0, 2);
    }


    @Override
    public void paintComponent(Graphics g) {
        clear(g);
        matrix.print();
        if (matrix != null) {
            int size = matrix.size();
            int frameRatio = dimension.width > size ? dimension.width / size : size / dimension.width;

            int minAbs = Math.abs(matrix.getMin());
            int maxAbs = Math.abs(matrix.getMax());

            int sizeAbs = maxAbs + minAbs;

            int colorRatio = 255 > sizeAbs ? 255 / sizeAbs : sizeAbs / 255;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int valueColor = (matrix.get(i, j) + minAbs) * colorRatio;
                    for (int z = valueColor; z > valueColor; z-=255){

                    }
                    colors[colorVariable] = valueColor % 255;
                    g.setColor(new Color(colors[0], colors[1], colors[2]));
                    int y = j * frameRatio;
                    for (int x = i * frameRatio; x < i * frameRatio + frameRatio; x++)
                        g.drawLine(x, y, x, (y + frameRatio));
                }
            }
        }
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
}
