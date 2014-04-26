package se.dolkow.graphbender.scene;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class SceneryUtils {
	private static final Random rand = new Random();
	private static final int WILDNESS = 10;
	
	public static void drawlineWithRandomWalk(Canvas c, int xs, int ys, int xe, int ye, Paint paint) {
		final int dx = xe-xs;
		final int dy = ye-ys;
		final int segments = Math.max(1, (int)(0.05*Math.sqrt(dx*dx + dy*dy)));
		int xw = (xs - xe) / segments;
		int yw = (ys - ye) / segments;
		int nx, ny;
		Path path = new Path();
		path.moveTo(xs, ys);
		for (int i = 0; i < segments; i++) {
			nx = xs - xw + (rand.nextBoolean() ? -1 : 1) * rand.nextInt(WILDNESS);
			ny = ys - yw + (rand.nextBoolean() ? -1 : 1) * rand.nextInt(WILDNESS);
			c.drawLine(xs, ys, nx, ny, paint);
			xs = nx;
			ys = ny;
		}
		if ((xs != xe) || (ys != ye)) {
			path.lineTo(xe, ye);
		}
		c.drawPath(path, paint);
	}
}
