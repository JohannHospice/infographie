package raycasting;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RayCasting {
	public static void createUI() {
		JFrame f = new JFrame("Ray Casting");
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		MyPanel p = new MyPanel(500);
		PanelMap pm = new PanelMap(300,300);
		Caster m = new Caster(p,pm);
		p.setModel(m);
		pm.setModel(m);
		f.setContentPane(panel);
		panel.add(p);
		panel.add(pm);
		f.pack();
		f.setVisible(true);
		m.cast();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->createUI());
	}

}
