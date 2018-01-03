package raycasting;

public class Point {
	private double x;
	private double y;
	public Point(double x,double y) {
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public String toString() {
		return "["+x+","+y+"]";
	}
	public Point gauche() {
		return new Point(this.getX()-1,this.getY());
	}
	public Point bas() {
		return new Point(this.getX(),this.getY()-1);
	}
	public Point translate(double distance,double direction) {
		return new Point(getX()+distance*Math.cos(direction),
			getY()+distance*Math.sin(direction));
	}
	public static double distance(Point p1,Point p2) {
		double dx = p1.getX()-p2.getX();
		double dy = p1.getY()-p2.getY();
		return Math.sqrt(dx*dx+dy*dy);
	}
}
