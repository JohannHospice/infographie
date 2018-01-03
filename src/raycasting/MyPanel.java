package raycasting;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JSlider;

public class MyPanel extends JPanel implements KeyListener {
	Dimension dimension;
	Caster model;
	public MyPanel(int w) {
		this.dimension = new Dimension(w,w);
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
		JSlider apertureSlider = new JSlider(10,160,(int)(Caster.ANGLE_OF_VIEW*180/Math.PI));
		apertureSlider.addChangeListener((e)->{
			model.setAngleOfView(apertureSlider.getValue()*Math.PI/180);
		});
		this.add(apertureSlider);
	}
	public void setModel(Caster model) {
		this.model = model;
	}
	@Override
	public Dimension getPreferredSize() {
		return dimension;
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			model.left();
			break;
		case KeyEvent.VK_RIGHT:
			model.right();
			break;
		case KeyEvent.VK_UP:
			model.forward();
			break;
		case KeyEvent.VK_DOWN:
			model.backward();
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(model.getImage(),0,0,null);
		requestFocus();
	}
}
