package raycasting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Caster {
	private static class Result {
		double angleOfRay;
		double distanceOfCollision;
		Result(double a,double d) {
			angleOfRay = a;
			distanceOfCollision = d;
		}
		public static Result min(Result r1,Result r2) {
			if (r1.distanceOfCollision<r2.distanceOfCollision)
				return r1;
			else
				return r2;
		}
	}
	public final static int TURNS_PER_ROUND = 25*4; // 100 makes one full spin, best is multiple of 4
	public final static double ROTATION_INCREMENT = 2*Math.PI/TURNS_PER_ROUND;
	public static double ANGLE_OF_VIEW = 2*Math.PI/3; // Human = 120°
	private static double FOCUS;
	private static final double SCREEN_SIZE = 0.25;
	static { computeFocus(); }
	public final static double MOVE_DISTANCE = 0.1;
	public final static double WALL_HEIGHT = 1;
	public final Color BACKGROUND = Color.WHITE;
	private Map map;
	private boolean showConeOfLight;
	private BufferedImage image;
	private Graphics graphics;
	private MyPanel view;
	private PanelMap mapView;
	private Point eye;
	private int turns;
	private double directionOfVision;
	public Caster(MyPanel p, PanelMap pm) {
		this.mapView = pm;
		this.view = p;
		this.image = new BufferedImage(this.view.getPreferredSize().width,
				this.view.getPreferredSize().height,
				BufferedImage.TYPE_INT_ARGB);
		this.graphics = this.image.getGraphics();
		this.map = new Map(this.mapView.getPreferredSize().width,
				this.mapView.getPreferredSize().height);
		this.eye = new Point(1.5,1.5);
		this.turns = 0;
		this.directionOfVision = 0;
	}
	public BufferedImage getImage() {
		return this.image;
	}
	public BufferedImage getImageMap() {
		return map.getImage();
	}
	private static void computeFocus() {
		FOCUS = SCREEN_SIZE/Math.tan(ANGLE_OF_VIEW/2);
	}
	public void setAngleOfView(double value) {
		ANGLE_OF_VIEW = value;
		System.out.println("Angle of view = "+ANGLE_OF_VIEW);
		computeFocus();
		cast();
	}
	private void updateTurns(int q) {
		turns = (turns+TURNS_PER_ROUND+q)%TURNS_PER_ROUND;
		this.directionOfVision = this.turns*ROTATION_INCREMENT;
		cast();
	}
	public void left() {
		if (this.view==null) return;
		updateTurns(+1);
		this.cast();
	}
	public void right() {
		if (this.view==null) return;
		updateTurns(-1);
		this.cast();
	}
	private void moveEye(double distance) {
		Point newEye = eye.translate(distance,directionOfVision);
		if (!map.isIn(newEye) || map.isBrick(newEye)) return;
		eye = newEye;
		this.cast();
	}
	public void forward() {
		moveEye(MOVE_DISTANCE);
	}
	public void backward() {
		moveEye(-MOVE_DISTANCE);
	}
	public void toggleConeOfLight() {
		showConeOfLight ^= true;
		cast();
	}
	private void castRay(int xOnScreen,double relativeAngleOfRay,double direction) {
		Result r = Result.min(castRayInX(relativeAngleOfRay+direction), castRayInY(relativeAngleOfRay+direction));
		// In case of out-of-space rays
		if (showConeOfLight) map.line(eye,r.angleOfRay,r.distanceOfCollision);
		if (r.distanceOfCollision==Double.POSITIVE_INFINITY) return;
		double distance = r.distanceOfCollision;
		// optical correction
		distance *= Math.cos(relativeAngleOfRay);
		// projection
		double hp = FOCUS*WALL_HEIGHT/distance;
		// convert to physical screen coordinates
		int vh = this.image.getHeight();
		int projectedHeight = (int)(hp*vh/SCREEN_SIZE);
		graphics.setColor(Color.BLACK);
		graphics.drawLine(xOnScreen,(vh-projectedHeight)/2,xOnScreen,(vh+projectedHeight)/2);
	}
	private Result castRayInX(double angleOfRay) {
		double cos = Math.cos(angleOfRay);
		if (cos>0) {
			Point p = null;
			for (int vx=(int)Math.floor(eye.getX())+1; vx<this.image.getWidth(); vx++) {
				p = new Point(vx,Math.tan(angleOfRay)*(vx-eye.getX())+eye.getY());
				if (map.isBrick(p))
					return new Result(angleOfRay, Point.distance(eye,p));
			}
			return new Result(angleOfRay,Double.POSITIVE_INFINITY);
		}
		else if (cos<0) {
			for (int vx=(int)Math.floor(eye.getX()); vx>0; vx--) {
				Point p = null;
				p = new Point(vx,Math.tan(angleOfRay)*(vx-eye.getX())+eye.getY());
				if (map.isBrick(p.gauche()))
					return new Result(angleOfRay, Point.distance(eye,p));
			}
			return new Result(angleOfRay,Double.POSITIVE_INFINITY);
		}
		else
			return new Result(angleOfRay,Double.POSITIVE_INFINITY);
	}
	private Result castRayInY(double angleOfRay) {
		double sin = Math.sin(angleOfRay);
		if (sin>0) {
			for (int vy=(int)Math.floor(eye.getY())+1; vy<this.image.getWidth(); vy++) {
				Point p = new Point((vy-eye.getY())/Math.tan(angleOfRay)+eye.getX(),vy);
				if (map.isBrick(p))
					return new Result(angleOfRay, Point.distance(eye,p));
			}
			return new Result(angleOfRay,Double.POSITIVE_INFINITY);
		}
		else if (sin<0) {
			for (int vy=(int)Math.floor(eye.getY()); vy>0; vy--) {
				Point p = new Point((vy-eye.getY())/Math.tan(angleOfRay)+eye.getX(),vy);
				if (map.isBrick(p.bas()))
					return new Result(angleOfRay, Point.distance(eye,p));
			}
			return new Result(angleOfRay,Double.POSITIVE_INFINITY);
		}
		else
			return new Result(angleOfRay,Double.POSITIVE_INFINITY);
	}
	private void refreshViews() {
		this.view.repaint();
		this.mapView.repaint();
	}
	public void cast() {
		System.out.println("Cast--------- eye="+eye+" vision="+directionOfVision);
		map.draw(eye,directionOfVision,FOCUS);
		graphics.setColor(Color.GRAY);
		graphics.fillRect(0,this.image.getHeight()/2,this.image.getWidth(),this.image.getHeight()/2);
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0,0,this.image.getWidth(),this.image.getHeight()/2);
		int w = this.image.getWidth();
		for (int x=0; x<w; x++) { // cast for every column of screen
			double angleOfRay = Math.atan(((-SCREEN_SIZE/(w-1)*x+SCREEN_SIZE/2))/FOCUS);
			castRay(x,angleOfRay,directionOfVision);
		}
		map.drawEye(eye, directionOfVision, FOCUS);
		refreshViews();
	}
}
