package raycasting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PanelMap extends JPanel {
	private Dimension dimension;
	private Caster model;
	public PanelMap(int w,int h) {
		this.dimension = new Dimension(w,h);
		this.setLayout(new FlowLayout());
		JRadioButton cone = new JRadioButton("Cone of light");
		this.add(cone);
		cone.addActionListener((e)->this.model.toggleConeOfLight());
	}
	public Dimension getPreferredSize() {
		return dimension;
	}
	public void setModel(Caster m) {
		this.model = m;
	}
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(model.getImageMap(), 0, 0, null);
	}
}
