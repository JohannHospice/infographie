package app.view.panel;

import java.awt.Color;
import java.awt.Graphics;

import app.modele.math.*;
import app.modele.shape.WorldObject;

public class WorldPanel extends GraphicPanel {
    private WorldObject object;

    public WorldPanel(int w, int h) {
        super(w, h);
    }

    public void setWorldObject(WorldObject c) {
        object = c;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        // clear
        clear(g);

        g.setColor(Color.BLACK);
        Matrix m = Matrix.createParallelZ();
        // Matrix.createPerspectiveZ(400);

        WorldObject currentObject = object;
        if (currentObject != null) {
            for (Segment s : currentObject) {
                Point b = s.getBegin();
                Point e = s.getEnd();

                // clip?
                b = Calculus.multiply(m, b);
                e = Calculus.multiply(m, e);

                // in case of parallel projection
                //   homogeneous coordinates are degenerated, take care of
                // in case of perspective projection
                //   may need to homogenize...
                if (b.getT() != 0) b.homogenize();
                if (e.getT() != 0) e.homogenize();

                // center on screen... (aka viewport transform)
                g.drawLine((int) (b.getX() + dimension.getWidth() / 2), (int) (b.getY() + dimension.getHeight() / 2),
                        (int) (e.getX() + dimension.getWidth() / 2), (int) (e.getY() + dimension.getHeight() / 2));
            }
        }
    }
}
