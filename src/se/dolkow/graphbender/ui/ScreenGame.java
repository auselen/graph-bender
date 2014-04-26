package se.dolkow.graphbender.ui;

import java.lang.ref.WeakReference;

import se.dolkow.graphbender.Metric;
import se.dolkow.graphbender.animation.Animator;
import se.dolkow.graphbender.animation.NullAnimator;
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
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

public class ScreenGame implements Screen {

	private static final long LEVEL_CHANGE_DELAY = 1000;
	
	private static final int MSG_RESTART_LEVEL     = 1;
	private static final int MSG_NEXT_LEVEL        = 2;
	
	private Logic mCurrentLogic;
	private Scenery mCurrentScenery;
	private Layout mLayout;
	private Layout mLevelInitLayout;
	private Animator mAnimator;
	
	private final Paint timePaint = new Paint();
	
	private float mTargetY;
	private float mTargetX;
	private int mLevel = 2;
	private long mLevelStartTime;
	private int mHeight;
	private int mWidth;

	private GameEngineHandler mHandler = new GameEngineHandler(this);
	
	public ScreenGame(ScreenManager screenManager) {
		super();
		mLayout = new PullInRingLayout();
		mLevelInitLayout = new SingularityLayout();
		mAnimator = new SpiralAnimator();
		mCurrentScenery = new Scenery();
		
		timePaint.setColor(Color.CYAN);
		timePaint.setTextSize(Metric.TIMESTAMP_SIZE);
		
		createLevel(mLevel);
	}
	public void createLevel(int n) {
		mCurrentLogic = new Logic(n);
		mLevelStartTime = System.nanoTime();
		mLevelInitLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
		new NullAnimator().update(mCurrentLogic, 0);
		mLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
	}
	
	public void update(long frameTime, long timeDeltaNano) {
		mAnimator.update(mCurrentLogic, timeDeltaNano);
	}

	@Override
	public void handleTouch(MotionEvent event) {
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
					mHandler.sendEmptyMessageDelayed(MSG_NEXT_LEVEL, LEVEL_CHANGE_DELAY);
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

	@Override
	public void draw(Canvas c, long frameTime, long deltaTime) {
		mCurrentScenery.draw(c, mCurrentLogic, mTargetX, mTargetY);
		c.drawText("" + ((frameTime - mLevelStartTime)/1000000)/1000.0, 5, Metric.TIMESTAMP_SIZE, timePaint);
	}

	@Override
	public void sizeChanged(int width, int height) {
		mWidth = width;
		mHeight = height;
		mLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
	}
	
	private static boolean hits(Vertex v, float x, float y) {
		return (Math.abs(v.x - x) < Metric.FINGER_SIZE) &&
				(Math.abs(v.y - y) < Metric.FINGER_SIZE);
	}
	
	public static class GameEngineHandler extends Handler {
		private final WeakReference<ScreenGame> weakGame;
		
		public GameEngineHandler(ScreenGame game) {
			weakGame = new WeakReference<ScreenGame>(game);
		}
		
		@Override
		public void handleMessage(Message msg) {
			ScreenGame game = weakGame.get();
			if (game == null) {
				return;
			}
		    switch(msg.what) {
		    	case MSG_NEXT_LEVEL:
		    		removeCallbacksAndMessages(null);
		    		game.createLevel(++game.mLevel);
		    		break;
		    	case MSG_RESTART_LEVEL:
		    		removeCallbacksAndMessages(null);
		    		game.createLevel(game.mLevel);
		    		break;
		    	default:
		    		throw new RuntimeException("Unhandled msg.what: " + msg.what);
		    }
		}
    }
}
