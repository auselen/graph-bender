package se.dolkow.graphbender.scene;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Paint;

public class SceneryUtils {
	private static final Random rand = new Random();
	
	public void drawlineWithRandomWalk(Canvas c, int xs, int ys, int xe, int ye,
			int segments, int wildness, Paint paint) {
		if (xs > xe) {
			int t = xs;
			xs = xe;
			xe = t;
		}
		if (ys > ye) {
			int t = ys;
			ys = ye;
			ye = t;
		}
		int xw = (xs - xe) / segments;
		int yw = (xs - xe) / segments;
		int nx, ny;
		for (int i = 0; i < segments; i++) {
			nx = xs + xw + (rand.nextBoolean() ? -1 * rand.nextInt(wildness) : rand.nextInt(wildness));
			ny = ys + yw + (rand.nextBoolean() ? -1 * rand.nextInt(wildness) : rand.nextInt(wildness));
			c.drawLine(xs, ys, nx, ny, paint);
			xs = nx;
			ys = ny;
		}
		if ((xs != xe) || (ys != ye)) {
			c.drawLine(xs, ys, xe, ye, paint);
		}
	}
}
