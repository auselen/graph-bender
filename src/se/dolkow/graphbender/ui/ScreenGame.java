package se.dolkow.graphbender.ui;

import se.dolkow.graphbender.GameSurface;
import se.dolkow.graphbender.Metric;
import se.dolkow.graphbender.animation.Animator;
import se.dolkow.graphbender.animation.SpiralAnimator;
import se.dolkow.graphbender.layout.Layout;
import se.dolkow.graphbender.layout.PullInRingLayout;
import se.dolkow.graphbender.layout.SingularityLayout;
import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;
import se.dolkow.graphbender.scene.Scenery;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class ScreenGame extends Screen {
	private Logic mCurrentLogic;
	private Scenery mCurrentScenery;
	private Layout mLayout;
	private Layout mLevelInitLayout;
	private Animator mAnimator;
	
	private float mTargetY;
	private float mTargetX;
	private int mLevel = 2;
	private int mWidth;
	private int mHeight;
	private long mLevelStartTime;
	private final FrameCallback frameCallback;
	private final Choreographer choreographer;
	
	public ScreenGame(GameSurface gameSurface) {
		super(gameSurface);
		mLayout = new PullInRingLayout();
		mLevelInitLayout = new SingularityLayout();
		mAnimator = new SpiralAnimator();
		mCurrentScenery = new Scenery();
		
		createLevel(mLevel);
		
		frameCallback = new FrameCallback();
		choreographer = Choreographer.getInstance();
		requestFrame();
	}
	
	public void createLevel(int n) {
		mCurrentLogic = new Logic(n);
		mLevelStartTime = System.nanoTime();
		mLevelInitLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
	}
	
	@Override
	public void draw(Canvas c) {
		mAnimator.update(mCurrentLogic, SystemClock.elapsedRealtimeNanos()); // TODO: use Choreographer as time source instead
		mCurrentScenery.draw(c, mCurrentLogic, mTargetX, mTargetY);
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		int action = event.getAction(); 
		float x = event.getX();
		float y = event.getY();
		if (action == MotionEvent.ACTION_DOWN) {
			int n = mCurrentLogic.getVertexCount();
			for (int i = 0; i < n; i++) {
				Vertex v = mCurrentLogic.getVertex(i);
				if (hits(v, x, y)) {
					if (v.required > 0) {
						v.selected = true;
						mTargetX = x;
						mTargetY = y;
					}
					break;
				}
			}
		} else if ((action == MotionEvent.ACTION_UP)
				|| (action == MotionEvent.ACTION_CANCEL)) {
			final int n = mCurrentLogic.getVertexCount();
			int selected = -1;
			int hovered = -1;
			for (int i = 0; i < n; i++) {
				Vertex v = mCurrentLogic.getVertex(i);
				if (v.selected)
					selected = i;
				if (v.hovered)
					hovered = i;
				v.selected = false;
				v.hovered = false;
			}
			if ((selected != -1) && (hovered != -1)) {
				mCurrentLogic.connect(selected, hovered);
				if (mCurrentLogic.satisfied()) {
					createLevel(++mLevel);
				}
				mLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
			}
		} else if (action == MotionEvent.ACTION_MOVE) {
			mTargetX = x;
			mTargetY = y;
			int n = mCurrentLogic.getVertexCount();
			for (int i = 0; i < n; i++) {
				Vertex v = mCurrentLogic.getVertex(i);
				if (hits(v, x, y)) {
					if (v.required > 0)
						v.hovered = true;
				} else {
					v.hovered = false;
				}
			}
		}
	}
	
	private void requestFrame() {
		choreographer.postFrameCallback(frameCallback);
	}
	
	class FrameCallback implements Choreographer.FrameCallback {
		private final Paint timePaint = new Paint();

		public FrameCallback() {
			timePaint.setColor(Color.CYAN);
			timePaint.setTextSize(Metric.TIMESTAMP_SIZE);
		}
		
		@Override
        public void doFrame(long time) {
			requestFrame(); // TODO: only do this if animator or scene tells us it's needed -- avoid redrawing unnecessarily.
			mAnimator.update(mCurrentLogic, time);
			if (mSurfaceAvailable) {
				SurfaceHolder holder = mGameSurface.getHolder();
				Canvas c = holder.lockCanvas();
				mCurrentScenery.draw(c, mCurrentLogic, mTargetX, mTargetY);
				c.drawText("" + ((time - mLevelStartTime)/1000000)/1000.0, 5, Metric.TIMESTAMP_SIZE, timePaint);
				holder.unlockCanvasAndPost(c);
			}
        }
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		super.surfaceChanged(holder, format, width, height);
		mLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
	}
	
	private static boolean hits(Vertex v, float x, float y) {
		return (Math.abs(v.x - x) < Metric.FINGER_SIZE) &&
				(Math.abs(v.y - y) < Metric.FINGER_SIZE);
	}
}
