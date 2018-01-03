package faceculling.MyMath;
import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

public class Face implements Iterable<Segment> {
	Vector<Point> points;
	int []indices;
	private Color color;
	public Face(int []idx,Vector<Point> ps,Color c) {
		indices = idx;
		points = ps;
		color = c;
	}
	public Face(int []idx,Vector<Point> ps) {
		this(idx,ps,null);
	}
	public Color getColor() {
		return color;
	}
	public int [] getIndices() {
		return indices.clone();
	}
	public faceculling.MyMath.Vector getVector(int idx) {
		Point p0 = points.get(indices[idx]); p0.homogenize();
		Point p1 = points.get(indices[(idx+1)%indices.length]); p1.homogenize();
//		System.out.println("Points = "+p0+" "+p1);
		return new faceculling.MyMath.Vector(p1.getX()-p0.getX(),p1.getY()-p0.getY(),p1.getZ()-p0.getZ());
	}
	public Point getPoint(int idx) {
		return points.get(indices[idx]);
	}
	public Iterator<Point> pointIterator() {
		return new Iterator<Point>() {
			int idx = 0;
			@Override
			public boolean hasNext() {
				return idx!=indices.length;
			}
			@Override
			public Point next() {
//				System.out.println("Iterate "+idx);
				Point p = points.get(indices[idx]);
				idx++;
				return p;
			}			
		};
	}
	public Iterator<Segment> iterator() {
		return new Iterator<Segment>() {
			int idx = 0;
			@Override
			public boolean hasNext() {
				return idx!=indices.length;
			}
			@Override
			public Segment next() {
//				System.out.println("Iterate "+idx);
				Segment s = new Segment(points.get(indices[idx]),points.get(indices[(idx+1)%indices.length]));
				idx++;
				return s;
			}			
		};
	}
}
