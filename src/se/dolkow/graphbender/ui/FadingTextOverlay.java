package se.dolkow.graphbender.ui;

import static se.dolkow.graphbender.util.Utils.linterp;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class FadingTextOverlay implements Overlay {

	private final String text;
	private final Paint textPaint = new Paint();
	private long ttl;
	private int height;
	private int width;
	private float textWidth;
	
	public FadingTextOverlay(String s) {
		text = s;
		ttl = 2000000000;
		textPaint.setColor(Color.YELLOW);
		textPaint.setTextSize(100);
		textPaint.setAntiAlias(true);
		textPaint.setShadowLayer(10, 10, 10, Color.BLACK);
		textPaint.setTypeface(Typeface.MONOSPACE);
		textWidth = textPaint.measureText(text);
	}
	
	@Override
    public void sizeChanged(int w, int h) {
	    width = w;
	    height = h;
	    while (textWidth > w) {
	    	textPaint.setTextSize((textPaint.getTextSize() / 3) * 2); 
	    	textWidth = textPaint.measureText(text);
	    }
    }

	@Override
    public void draw(Canvas c, long frameTime, long deltaTime) {
		textPaint.setAlpha((int)(linterp(0, 255, ttl, 0, 1000000000)));
		c.drawText(text, (width-textWidth)/2, height/2, textPaint);
    }

	@Override
    public boolean update(long frameTime, long deltaTime) {
	    ttl -= deltaTime;
	    return ttl > 0;
    }

}
