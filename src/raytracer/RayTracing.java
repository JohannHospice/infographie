package raytracer;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RayTracing {
	public static void createUI() {
		JFrame f = new JFrame("Ray Tracing");
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		f.setContentPane(panel);

		MyPanel p = new MyPanel(800,800);
		panel.add(p);
		
		RayTracer r = new RayTracer(p);		
		Thread t = new Thread(r);
		t.start();
		
		f.pack();
		f.setVisible(true);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(RayTracing::createUI);
	}

}
