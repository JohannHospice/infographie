package faceculling;

import faceculling.MyMath.Point;

import java.awt.*;

public class Sphere extends WorldObject {
    public Sphere(int latitudes, int longitudes, double rayon) {
        double alpha = Math.PI / (2 * latitudes);
        double beta = 2 * Math.PI / longitudes;
        int i = 0;
        Color colors[] = new Color[2];
        colors[0] = Color.RED;
        colors[1] = Color.GREEN;
        /*
		colors[2] = Color.BLUE;
		colors[3] = Color.MAGENTA;
		colors[4] = Color.CYAN;
		colors[5] = Color.ORANGE;
		colors[6] = Color.YELLOW;
		colors[7] = Color.PINK;
		*/
        for (int t = 0; t < latitudes; t++) {
            double rt = rayon * Math.cos(t * alpha);
            double rt_1 = rayon * Math.cos((t + 1) * alpha);
            double y = rayon * Math.sin(t * alpha);
            double y_1 = rayon * Math.sin((t + 1) * alpha);
            for (int f = 0; f < longitudes; f++) {
                double xrt = rt * Math.cos(f * beta);
                double zrt = rt * Math.sin(f * beta);
                double x_1rt = rt * Math.cos((f + 1) * beta);
                double z_1rt = rt * Math.sin((f + 1) * beta);
                double xrt_1 = rt_1 * Math.cos(f * beta);
                double zrt_1 = rt_1 * Math.sin(f * beta);
                double x_1rt_1 = rt_1 * Math.cos((f + 1) * beta);
                double z_1rt_1 = rt_1 * Math.sin((f + 1) * beta);
                Point prt = new Point(xrt, y, zrt);
                Point p_1rt = new Point(x_1rt, y, z_1rt);
                Point prt_1 = new Point(xrt_1, y_1, zrt_1);
                Point p_1rt_1 = new Point(x_1rt_1, y_1, z_1rt_1);
                add(prt);
                add(p_1rt);
                add(prt_1);
                add(p_1rt_1);
//				add(colors[(f+t)%colors.length],i+3,i+1,i+0,i+2);
                add(colors[(t) % colors.length], i + 2, i + 0, i + 1, i + 3);
//				add(null,i+3,i+1,i+0,i+2);
                i += 4;
                Point sprt = new Point(xrt, -y, zrt);
                Point sp_1rt = new Point(x_1rt, -y, z_1rt);
                Point sprt_1 = new Point(xrt_1, -y_1, zrt_1);
                Point sp_1rt_1 = new Point(x_1rt_1, -y_1, z_1rt_1);
                add(sprt);
                add(sp_1rt);
                add(sprt_1);
                add(sp_1rt_1);
//				add(colors[(f+t+1)%colors.length],i+2,i+0,i+1,i+3);
                add(colors[(f) % colors.length], i + 1, i + 0, i + 2, i + 3);
//				add(null,i+2,i+0,i+1,i+3);
                i += 4;
            }
        }
    }
}
