package se.dolkow.graphbender;

import se.dolkow.graphbender.animation.Animator;
import se.dolkow.graphbender.animation.ConstantSpeedAnimator;
import se.dolkow.graphbender.animation.ProportionalAnimator;
import se.dolkow.graphbender.animation.SpiralAnimator;
import se.dolkow.graphbender.layout.Layout;
import se.dolkow.graphbender.layout.PullInRingLayout;
import se.dolkow.graphbender.layout.RingLayout;
import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;
import se.dolkow.graphbender.scene.Scenery;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

/*
 * Keeps the game flow
 */
public class GameEngine implements Callback {
	private static final String TAG = "GameEngine";
	private Logic mCurrentLogic;
	private Scenery mCurrentScenery;
	private GameSurface mGameSurface;
	private Layout mLayout;
	private Animator mAnimator;
	private volatile boolean mSurfaceAvailable;
	private InputHandler mInputHandler;
	private float mTargetY;
	private float mTargetX;
	private int mLevel = 2;
	private int mWidth;
	private int mHeight;

	public GameEngine(GameSurface surface) {
		mGameSurface = surface;
		mGameSurface.getHolder().addCallback(this);
		mInputHandler = new InputHandler(this);
		mGameSurface.register(mInputHandler);
		createLevel(mLevel);
		new GraphLoop().start();
	};

	public void createLevel(int n) {
		mCurrentLogic = new Logic(n);
		mCurrentScenery = new Scenery();
		mLayout = new PullInRingLayout();
		mAnimator = new SpiralAnimator();
	}

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
			int n = mCurrentLogic.getVertexCount();
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
				if (mCurrentLogic.satisfied())
					mCurrentLogic = new Logic(++mLevel);
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

	private static boolean hits(Vertex v, float x, float y) {
		return (Math.abs(v.x - x) < Metric.FINGER_SIZE) &&
				(Math.abs(v.y - y) < Metric.FINGER_SIZE);
	}

	class GraphLoop extends Thread {
		@Override
		public void run() {
			Paint timePaint = new Paint();
			timePaint.setColor(Color.CYAN);
			timePaint.setTextSize(Metric.TIMESTAMP_SIZE);
			long startTime = System.nanoTime();
			while (true) {
				long time = System.nanoTime();
				mAnimator.update(mCurrentLogic, time); // TODO: use Choreographer as time source instead
				if (mSurfaceAvailable) {
					SurfaceHolder holder = mGameSurface.getHolder();
					Canvas c = holder.lockCanvas();
					mCurrentScenery.draw(c, mCurrentLogic, mTargetX, mTargetY);
					c.drawText("" + (time - startTime), 5, Metric.TIMESTAMP_SIZE, timePaint);
					holder.unlockCanvasAndPost(c);
				}
				try {
					sleep(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mWidth = width;
		mHeight = height;
		mLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mSurfaceAvailable = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mSurfaceAvailable = false;
	}
}
