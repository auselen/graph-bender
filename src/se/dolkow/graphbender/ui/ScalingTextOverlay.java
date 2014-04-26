package se.dolkow.graphbender.ui;

import se.dolkow.graphbender.Metric;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path.Direction;

public class ScalingTextOverlay implements Overlay {

	private final String text;
	private final Paint textPaint = new Paint();
	private int width;
	private int height;
	private float textWidth;
	private int pos;
	private int textSize;
	private boolean dir;
	
	public ScalingTextOverlay(String s, boolean direction) {
		text = s;
		textPaint.setColor(Color.YELLOW);
		textPaint.setAntiAlias(true);
		dir = direction;
		textSize = direction ? 200 : 10;
		textPaint.setTextSize(textSize);
		textWidth = textPaint.measureText(text);
		pos = (int) textWidth;
	}

	@Override
    public void sizeChanged(int w, int h) {
	    width = w;
	    height = h;
    }

	@Override
    public void draw(Canvas c, long frameTime, long deltaTime) {
		c.drawText(text, (width-textWidth)/2, (height - textSize)/2, textPaint);
    }

	@Override
    public boolean update(long frameTime, long deltaTime) {
		textSize += dir ? -5*Metric.SCALE : 5*Metric.SCALE;
		textPaint.setTextSize(textSize);
		textWidth = textPaint.measureText(text);
	    return dir ? textSize > 0 : textSize < 200;
    }
}
