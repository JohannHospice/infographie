package raycasting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Map {
	private BufferedImage image;
	private Graphics graphics;
	private double cx;
	private double cy;
	private char [][]map = {  // each: SQUARE_SIZE x SQUARE_SIZE
			{ 'Y', 'Z', 'X', 'Y', 'Z', 'X', 'X' },
			{ 'X', ' ', ' ', ' ', 'Y', ' ', 'X' },
			{ 'Z', ' ', 'X', ' ', 'X', ' ', 'X' },
			{ 'Y', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', 'Z', ' ', 'Y', 'X', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ' },
			{ 'X', 'Y', 'Y', 'X', 'Y', 'Y', 'X' }
	};
	public Map(int w,int h) {
		image = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		graphics = image.getGraphics();
		cx = (double)image.getWidth()/getWidth();
		cy = (double)image.getHeight()/getHeight();
	}
	public void line(Point p, double angle, double distance) {
		if (distance==Double.POSITIVE_INFINITY) distance = 1000;
		double x = p.getX()+distance*Math.cos(angle);
		double y = p.getY()+distance*Math.sin(angle);
		graphics.setColor(Color.YELLOW);
		graphics.drawLine((int)(p.getX()*cx), (int)((getHeight()-p.getY())*cy),
				(int)(x*cx), (int)((getHeight()-y)*cy));
	}
	public void draw(Point eye, double directionOfVision, double focus) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0,0,image.getWidth(),image.getHeight());
		for (int y=0; y<getHeight(); y++) {
			for (int x=0; x<getWidth(); x++) {
				Color c;
				if (isBrick(new Point(x,y))) c = Color.BLACK;
				else c = Color.WHITE;
				graphics.setColor(c);
				double mapSizeW = (double)image.getWidth()/getWidth();;
				double mapSizeH = (double)image.getHeight()/getHeight();;
				int rx = (int)(x*mapSizeW);
				int ry = (int)((getHeight()-1-y)*mapSizeH);
				graphics.fillRect(rx, ry, (int)mapSizeW+1, (int)mapSizeH+1);
			}
		}
	}
	public void drawEye(Point eye, double directionOfVision, double focus) {
		int ex = (int)(eye.getX()*cx);
		int ey = image.getHeight()-1-(int)(eye.getY()*cy);
		graphics.setColor(Color.BLACK);
		graphics.fillRect(ex-2,ey-2,5,5);
		int dx = (int)(ex+focus*cx*Math.cos(directionOfVision));
		int dy = (int)(ey-focus*cy*Math.sin(directionOfVision));
		graphics.drawLine(ex, ey, dx, dy);
	}
	public boolean isIn(Point p) {
		int x = (int)p.getX();
		int y = (int)p.getY();
		return x>=0 && x<map[0].length && y>=0 && y<map.length;
	}
	public boolean isBrick(Point p) {
		if (!isIn(p)) return false;
		int x = (int)p.getX();
		int y = getWidth()-1-(int)p.getY();
		return map[y][x]!=' ';
	}
	public int getHeight() {
		return map.length;
	}
	public int getWidth() {
		return map[0].length;
	}
	public BufferedImage getImage() {
		return image;
	}
}
