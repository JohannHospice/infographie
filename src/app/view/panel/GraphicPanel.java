package app.view.panel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

abstract class GraphicPanel extends JPanel {
    protected Dimension dimension = new Dimension();

    public GraphicPanel(int w, int h) {
        setSize(w, h);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }

    public void setSize(int w, int h) {
        dimension.setSize(w, h);
    }

    @Override
    public Dimension getPreferredSize() {
        return dimension;
    }

    @Override
    public abstract void paintComponent(Graphics g);

    public void clear(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, dimension.width, dimension.height);
    }
}
