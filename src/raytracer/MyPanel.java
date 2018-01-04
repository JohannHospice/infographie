package raytracer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel {
    private Dimension dimension;
    private BufferedImage image;

    public MyPanel(int w, int h) {
        dimension = new Dimension(w, h);
    }

    public Dimension getPreferredSize() {
        return dimension;
    }

    public void setImage(BufferedImage i) {
        image = i;
        repaint();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
}
