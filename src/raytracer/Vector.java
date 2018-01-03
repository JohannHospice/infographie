package raytracer;

public class Vector {
	public double x,y,z;
	public Vector(double x,double y,double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector multiply(double s) {
		return new Vector(x*s,y*s,z*s);
	}
	public String toString() {
		return "["+x+","+y+","+z+"]";
	}
	public void normalize() {
		double n = Math.sqrt(x*x+y*y+z*z);
		x /= n;
		y /= n;
		z /= n;
	}
	public void normalize(double value) {
		double n = Math.sqrt(x*x+y*y+z*z);
		double factor = value/n;
		x /= factor;
		y /= factor;
		z /= factor;
	}
	public double sq() {
		return x*x+y*y+z*z;
	}
	public Vector negate() {
		return new Vector(-x,-y,-z);
	}
	public double dp(Vector v) {
		return x*v.x+y*v.y+z*v.z;
	}
	public Vector plus(Vector v) {
		return new Vector(x+v.x,y+v.y,z+v.z);
	}
}
