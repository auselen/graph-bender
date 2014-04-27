package se.dolkow.graphbender.ui;

import se.dolkow.graphbender.Globals;

import se.dolkow.graphbender.Metric;
import se.dolkow.graphbender.R;
import se.dolkow.graphbender.animation.Animator;
import se.dolkow.graphbender.animation.NullAnimator;
import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;
import se.dolkow.graphbender.scene.FirstScenery;
import se.dolkow.graphbender.scene.Scenery;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class TitleScreen implements Screen {
	private Paint mPaintTitle;
	private final RenderableManager mScreenManager;
	private TextPaint mTextPaint;
	
	private final Scenery mScenery;
	private final Logic mTutorialLogic;
	
	private static final String STR_TITLE = "Jelly Hugs";
	private static final String TAG = "ScreenTitle";
	private static final String STR_DESC = "give those Jellyfishes some love!\n"
			+ "you need to reach level " + GameScreen.GOAL_LEVEL + "\n"
			+ "to save the jellyfish population!\n"
			+ "\n<tap on screen to begin your quest>";
	
	public TitleScreen(RenderableManager screenManager) {
		Log.d(TAG, "ScreenTitle");
		mPaintTitle = new Paint();
		mPaintTitle.setTextSize(Metric.SCALE * 40);
		mPaintTitle.setTypeface(Typeface.MONOSPACE);
		mPaintTitle.setColor(Color.WHITE);
		mScreenManager = screenManager;
		mTextPaint = new TextPaint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(Metric.SCALE * 16);
		mTextPaint.setTextAlign(Align.CENTER);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setDither(true);
		
		mScenery = new FirstScenery();
		
		mTutorialLogic = new Logic(4);
		final Vertex v0 = mTutorialLogic.getVertex(0);
		final Vertex v1 = mTutorialLogic.getVertex(1);
		final Vertex v2 = mTutorialLogic.getVertex(2);
		final Vertex v3 = mTutorialLogic.getVertex(3);
		v0.personality = v2.personality = 0;
		v1.personality = v3.personality = 1;
		v0.required = v2.required = 1;
		v1.required = v3.required = 3;
		mTutorialLogic.connect(2, 3);
	}

	@Override
	public void draw(Canvas c, long time, long delta) {
		
		mScenery.draw(c, mTutorialLogic, -1, -1, -1, -1);
		
		final int w = c.getWidth();
		final int stride = w/8;
		final int h = c.getHeight();
		
		StaticLayout mDescLayout = new StaticLayout(STR_DESC, mTextPaint,
	            c.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
		
		int x = (int) ((w - mPaintTitle.measureText(STR_TITLE)) / 2);
		int y = h / 4;
		c.drawText(STR_TITLE, x, y, mPaintTitle);
		c.save();
		c.translate(w / 2, h - mDescLayout.getHeight() - 20 * Metric.SCALE);
		mDescLayout.draw(c);
		c.restore();
		
		x = (int)((w - mPaintTitle.measureText("+")) / 2);
		c.drawText("+", x-2*stride, h/2+24, mPaintTitle);
		
		x = (int)((w - mPaintTitle.measureText("=")) / 2);
		c.drawText("=", x, h/2+24, mPaintTitle);
	}
	
	@Override
	public boolean handleKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode != KeyEvent.KEYCODE_BACK) {
			mScreenManager.startGame();
			return true;
		}
		return false;
	};

	@Override
	public void handleTouch(MotionEvent event) {
		mScreenManager.startGame();
	}

	@Override
    public void sizeChanged(int width, int height) {
		new TutorialLayout().updateDesiredPositions(mTutorialLogic, width, height);
    }

	@Override
    public void update(long frameTime, long deltaTime) {
    }

	@Override
    public boolean onBackPressed() {
	    return false;
    }
	
	private static class TutorialLayout implements se.dolkow.graphbender.layout.Layout {

		@Override
        public void updateDesiredPositions(Logic logic, int width, int height) {
	        final int stride = width/8;
	        final int N = logic.getVertexCount();
	        for (int i=0; i<N; ++i) {
	        	final Vertex v = logic.getVertex(i);
	        	v.x = stride * (1 + 2*i);
	        	v.y = height/2;
	        }
        }
		
	}
}
