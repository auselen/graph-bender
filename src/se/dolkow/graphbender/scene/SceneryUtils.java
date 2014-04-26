package se.dolkow.graphbender.scene;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Paint;

public class SceneryUtils {
	private static final Random rand = new Random();
	
	public static void drawlineWithRandomWalk(Canvas c, int xs, int ys, int xe, int ye,
			int segments, Paint paint) {
		int wildness = 10;
		int xw = (xs - xe) / segments;
		int yw = (ys - ye) / segments;
		int nx, ny;
		for (int i = 0; i < segments; i++) {
			nx = xs - xw + (rand.nextBoolean() ? -1 : 1) * rand.nextInt(wildness);
			ny = ys - yw + (rand.nextBoolean() ? -1 : 1) * rand.nextInt(wildness);
			c.drawLine(xs, ys, nx, ny, paint);
			xs = nx;
			ys = ny;
		}
		if ((xs != xe) || (ys != ye)) {
			c.drawLine(xs, ys, xe, ye, paint);
		}
	}
}
