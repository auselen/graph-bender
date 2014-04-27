package se.dolkow.graphbender.ui;

import se.dolkow.graphbender.Metric;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class ScalingTextOverlay implements Overlay {

	private final String text;
	private final Paint textPaint = new Paint();
	private int width;
	private int height;
	private float textWidth;
	private int textSize;
	private boolean dir;
	private boolean mBottom;
	
	public ScalingTextOverlay(String s, boolean direction) {
		text = s;
		textPaint.setColor(Color.WHITE);
		textPaint.setAntiAlias(true);
		textPaint.setShadowLayer(10, 10, 10, Color.BLACK);
		dir = direction;
		textSize = direction ? 200 : 10;
		textPaint.setTextSize(textSize);
		textPaint.setTypeface(Typeface.MONOSPACE);
		textWidth = textPaint.measureText(text);
	}

	public ScalingTextOverlay(String s, boolean b, boolean bottomOrTop) {
		this(s, b);
		mBottom = bottomOrTop;
	}

	@Override
    public void sizeChanged(int w, int h) {
	    width = w;
	    height = h;
    }

	@Override
    public void draw(Canvas c, long frameTime, long deltaTime) {
		c.drawText(text, (width-textWidth)/2, (height - textSize) / (mBottom ? 1 : 2), textPaint);
    }

	@Override
    public boolean update(long frameTime, long deltaTime) {
		textSize += (dir ? -1 : 1) * Metric.SCALE * deltaTime * 0.0000001;
		textPaint.setTextSize(textSize);
		textWidth = textPaint.measureText(text);
	    return dir ? textSize > 0 : textSize < 200;
    }
}
