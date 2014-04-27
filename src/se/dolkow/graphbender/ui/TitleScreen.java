package se.dolkow.graphbender.ui;

import se.dolkow.graphbender.Metric;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;

public class TitleScreen implements Screen {
	private Paint mPaintTitle;
	private final RenderableManager mScreenManager;
	private Paint mPaintDesc;
	private TextPaint mTextPaint;
	
	private static final String STR_TITLE = "Jellyfish Hugger";
	private static final String TAG = "ScreenTitle";
	private static final String STR_DESC = "give those Jellyfishes some love!\n"
			+ "you need to reach level " + GameScreen.GOAL_LEVEL + "\n"
			+ "to save the princess!\n"
			+ "\n<tap on screen to begin your quest>";
	
	public TitleScreen(RenderableManager screenManager) {
		Log.d(TAG, "ScreenTitle");
		mPaintTitle = new Paint();
		mPaintTitle.setTextSize(Metric.SCALE * 20);
		mPaintTitle.setTypeface(Typeface.MONOSPACE);
		mPaintTitle.setColor(Color.WHITE);
		mPaintDesc = new Paint();
		mPaintDesc.setTextSize(Metric.SCALE * 16);
		mPaintDesc.setTypeface(Typeface.SANS_SERIF);
		mPaintDesc.setColor(Color.WHITE);
		mScreenManager = screenManager;
		mTextPaint = new TextPaint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(Metric.SCALE * 16);
		mTextPaint.setTextAlign(Align.CENTER);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setDither(true);
	}

	@Override
	public void draw(Canvas c, long time, long delta) {
		final int w = c.getWidth();
		final int h = c.getHeight();
		
		StaticLayout mDescLayout = new StaticLayout(STR_DESC, mTextPaint,
	            c.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
		
		c.drawColor(Color.MAGENTA);
		int x = (int) ((w - mPaintTitle.measureText(STR_TITLE)) / 2);
		int y = h / 2;
		c.drawText(STR_TITLE, x, y, mPaintTitle);
		c.save();
		c.translate(w / 2, h - mDescLayout.getHeight() - 20 * Metric.SCALE);
		mDescLayout.draw(c);
		c.restore();
	}
	
	@Override
	public void handleKeyDown(int keyCode, android.view.KeyEvent event) {
		mScreenManager.startGame();
	};

	@Override
	public void handleTouch(MotionEvent event) {
		mScreenManager.startGame();
	}

	@Override
    public void sizeChanged(int width, int height) {
    }

	@Override
    public void update(long frameTime, long deltaTime) {
	    // TODO Auto-generated method stub
	    
    }
}
