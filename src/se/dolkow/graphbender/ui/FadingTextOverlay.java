package se.dolkow.graphbender.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.Log;

import static se.dolkow.graphbender.util.Utils.linterp;

public class FadingTextOverlay implements Overlay {

	private final String text;
	private final Paint textPaint = new Paint();
	private long ttl;
	private int height;
	private int width;
	private final float textWidth;
	
	public FadingTextOverlay(String s) {
		text = s;
		ttl = 2000000000;
		textPaint.setColor(Color.YELLOW);
		textPaint.setTextSize(300);
		textWidth = textPaint.measureText(text);
	}
	
	@Override
    public void sizeChanged(int w, int h) {
	    width = w;
	    height = h;
    }

	@Override
    public void draw(Canvas c, long frameTime, long deltaTime) {
		textPaint.setAlpha((int)(linterp(0, 255, ttl, 0, 1000000000)));
		Log.i("FADETEXT", "Yes, I'm here " + width + ", " + height + ", " + textPaint.getAlpha());
		c.drawText(text, (width-textWidth)/2, height/2, textPaint);
    }

	@Override
    public boolean update(long frameTime, long deltaTime) {
	    ttl -= deltaTime;
	    return ttl > 0;
    }

}