package se.dolkow.graphbender.ui;

import se.dolkow.graphbender.Metric;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class ScrollingTextOverlay implements Overlay {

	private final String text;
	private final Paint textPaint = new Paint();
	private int height;
	private final float textWidth;
	private int pos;
	
	public ScrollingTextOverlay(String s) {
		text = s;
		textPaint.setColor(Color.YELLOW);
		textPaint.setTextSize(200);
		textPaint.setAntiAlias(true);
		textPaint.setShadowLayer(10, 10, 10, Color.BLACK);
		textPaint.setTypeface(Typeface.MONOSPACE);
		textWidth = textPaint.measureText(text);
	}

	@Override
    public void sizeChanged(int w, int h) {
	    pos = w;
	    height = h;
    }

	@Override
    public void draw(Canvas c, long frameTime, long deltaTime) {
		c.drawText(text, pos, height/2, textPaint);
    }

	@Override
    public boolean update(long frameTime, long deltaTime) {
	    pos -= (int)(Metric.SCALE * 0.0000006 * deltaTime);
	    return pos > -textWidth;
    }
}
