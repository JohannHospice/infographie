package faceculling.MyMath;

public class Segment {
	private Point begin, end;
	public Segment(Point begin, Point end) {
		this.begin = begin;
		this.end = end;
	}
	public Point getBegin() {
		return begin;
	}
	public Point getEnd() {
		return end;
	}
	public String toString() {
		return "{"+begin+","+end+"}";
	}
	public Vector getVector() {		
		return new Vector((end.getX()/end.getT())-(begin.getX()/begin.getT()),
				(end.getY()/end.getT())-(begin.getY()/begin.getT()),
				(end.getZ()/end.getT())-(begin.getZ()/begin.getT()));
	}
}
