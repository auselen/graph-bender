package se.dolkow.graphbender.ui;

import java.lang.ref.WeakReference;

import se.dolkow.graphbender.Globals;
import se.dolkow.graphbender.Metric;
import se.dolkow.graphbender.animation.Animator;
import se.dolkow.graphbender.animation.NullAnimator;
import se.dolkow.graphbender.animation.ScrollAwayAnimator;
import se.dolkow.graphbender.animation.SpiralAnimator;
import se.dolkow.graphbender.layout.Layout;
import se.dolkow.graphbender.layout.PullInRingLayout;
import se.dolkow.graphbender.layout.SingularityLayout;
import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;
import se.dolkow.graphbender.scene.FirstScenery;
import se.dolkow.graphbender.scene.backgrounds.PictureBackground;
import se.dolkow.graphbender.util.TextGenerator;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GameScreen implements Screen {

	private static final long LEVEL_CHANGE_DELAY = 1000;
	private static final long FAILANIM_DELAY     = 1500;
	
	private static final int MSG_RESTART_LEVEL     = 1;
	private static final int MSG_NEXT_LEVEL        = 2;
	private static final int MSG_START_FAILANIM    = 3;

	private static final String TAG = "GameScreen";

	public static final int GOAL_LEVEL = 5;
	
	private Logic mCurrentLogic;
	private FirstScenery mCurrentScenery;
	private Layout mLayout = new SingularityLayout();
	private Animator mAnimator;
	
	private final Paint timePaint = new Paint();

	private int mTargetX;
	private int mTargetY;
	private int mLevel = 2;
	private long mLevelStartTime;
	private int mHeight;
	private int mWidth;

	private GameEngineHandler mHandler = new GameEngineHandler(this);

	private RenderableManager mScreenManager;

	private int mSelected;

	private int mHovered;

	private long mLevelFinishTime;
	
	public GameScreen(RenderableManager screenManager) {
		super();
		mAnimator = new SpiralAnimator();
		mCurrentScenery = new FirstScenery();
		mScreenManager = screenManager;
		
		timePaint.setColor(Color.YELLOW);
		timePaint.setTextSize(Metric.TIMESTAMP_SIZE);
		timePaint.setTypeface(Typeface.MONOSPACE);
		timePaint.setAntiAlias(true);
		timePaint.setDither(true);
		timePaint.setShadowLayer(3, 3, 3, Color.WHITE);
	}
	
	public void restart() {
		mLevel = 2;
		createLevel(mLevel);
		mLevelStartTime = System.nanoTime();
		mLevelFinishTime = -1;
	}

	public void createLevel(int n) {
		mCurrentLogic = new Logic(n);
		
		mLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
		new NullAnimator().update(mCurrentLogic, 0);
		mAnimator = new SpiralAnimator();
		mLayout = new PullInRingLayout();
		mLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
	}
	
	@Override
	public void update(long frameTime, long timeDeltaNano) {
		mAnimator.update(mCurrentLogic, timeDeltaNano);
	}
	
	private int wrap(int val) {
		final int N = mCurrentLogic.getVertexCount(); 
		val = val % N;
		if (val < 0) {
			val += N;
		}
		return val;
	}
	
	@Override
	public boolean handleKeyDown(int keyCode, android.view.KeyEvent event) {
		Log.d(TAG, "key down " + keyCode + " " + event);
		switch (keyCode) {
			case KeyEvent.KEYCODE_BUTTON_L1: 
				int n = wrap(mSelected-1);
				if (n == mHovered)
					n = wrap(n-1);
				mSelected = n;
				if (mHovered == -1)
					mHovered = wrap(mSelected+1);
				break;
			case KeyEvent.KEYCODE_BUTTON_R1:
				n = wrap(mSelected+1);
				if (n == mHovered)
					n = wrap(n+1);
				mSelected = n;
				if (mHovered == -1)
					mHovered = wrap(mSelected+1);
				break;
			case KeyEvent.KEYCODE_BUTTON_L2:
				n = wrap(mHovered-1);
				if (n == mSelected)
					n = wrap(n-1);
				mHovered = n;
				if (mSelected == -1)
					mSelected = wrap(mHovered+1);
				break;
			case KeyEvent.KEYCODE_BUTTON_R2:
				n = wrap(mHovered-1);
				if (n == mSelected)
					n = wrap(n-1);
				mHovered = n;
				if (mSelected == -1)
					mSelected = wrap(mHovered+1);
				break;
				/*
			case KeyEvent.KEYCODE_DPAD_UP:
				mTargetY -= 10;
				moveCheck();
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				mTargetY += 10;
				moveCheck();
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				mTargetX -= 10;
				moveCheck();
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				mTargetX += 10;
				moveCheck();
				break;
				*/
			case KeyEvent.KEYCODE_BUTTON_A:
				dragEnded(true);
				break;
			case KeyEvent.KEYCODE_BACK:
				mScreenManager.backPressed();
				break;
			default:
				return false;
		}
		if (mHovered != -1) {
			Vertex hovered = mCurrentLogic.getVertex(mHovered);
			mTargetX = hovered.x;
			mTargetY = hovered.y;
		}
		return true;
	}
	
	@Override
	public void handleTouch(MotionEvent event) {
		Log.d(TAG, "event " + event);
		int action = event.getAction(); 
		mTargetX = (int)event.getX();
		mTargetY = (int)event.getY();
		if (action == MotionEvent.ACTION_DOWN) {
			int n = mCurrentLogic.getVertexCount();
			for (int i = 0; i < n; i++) {
				Vertex v = mCurrentLogic.getVertex(i);
				if (hits(v, mTargetX, mTargetY)) {
					mSelected = i;
					break;
				}
			}
		} else if (action == MotionEvent.ACTION_UP) {
			dragEnded(true);
		} else if (action == MotionEvent.ACTION_CANCEL) {
			dragEnded(false);
		} else if (action == MotionEvent.ACTION_MOVE) {
			moveCheck();
		}
	}

	private void moveCheck() {
		int n = mCurrentLogic.getVertexCount();
		mHovered = -1;
		if (mSelected < 0) {
			return; // nothing to do
		}
		int chosenRequired = -1;
		for (int i = 0; i < n; i++) {
			Vertex v = mCurrentLogic.getVertex(i);
			if (i != mSelected && hits(v, mTargetX, mTargetY)) {
				if (v.required > chosenRequired) {
					mHovered = i;
					chosenRequired = v.required;
				}
			}
		}
	}

	private void dragEnded(boolean connect) {
		if (connect && mSelected != -1 && mHovered != -1) {
			if (mCurrentLogic.areConnectable(mSelected, mHovered)) {
				mScreenManager.bzzz();
				mCurrentLogic.connect(mSelected, mHovered);
				if (mCurrentLogic.satisfied()) {
					mLayout = new SingularityLayout();
					if (mLevel < GOAL_LEVEL) {
						mHandler.sendEmptyMessageDelayed(MSG_NEXT_LEVEL, LEVEL_CHANGE_DELAY);
						mScreenManager.addOverlay(new ScalingTextOverlay("Level " + mLevel, true, true));
						mScreenManager.addOverlay(OverlayFactory.getRandom(TextGenerator.win()));
					} else {
						mLevelFinishTime = System.nanoTime() - mLevelStartTime;
						mScreenManager.addOverlay(new ScalingTextOverlay("You won! " + mLevelFinishTime, true));
						Editor editor = Globals.sSharedPrefs.edit();
						editor.putString(Globals.SCORE, "" + mLevelFinishTime);
						editor.commit();
					}
				}
			}
			if (!mCurrentLogic.satisfiable()) {
				mScreenManager.addOverlay(OverlayFactory.getRandom(TextGenerator.lose()));
				mHandler.sendEmptyMessageDelayed(MSG_START_FAILANIM, FAILANIM_DELAY);
				mHandler.sendEmptyMessageDelayed(MSG_RESTART_LEVEL, FAILANIM_DELAY + LEVEL_CHANGE_DELAY);
			}
			mLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
		}
		mSelected = mHovered = -1;
	}

	@Override
	public void draw(Canvas c, long frameTime, long deltaTime) {
		mCurrentScenery.draw(c, mCurrentLogic, mTargetX, mTargetY, mSelected, mHovered);
		c.drawText("" + (mLevel-1) + "@" + ((frameTime - mLevelStartTime)/1000000)/1000.0, 5,
				Metric.TIMESTAMP_SIZE, timePaint);
	}

	@Override
	public void sizeChanged(int width, int height) {
		mWidth = width;
		mHeight = height;
		mLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
	}
	
	private static boolean hits(Vertex v, int x, int y) {
		return (Math.abs(v.x - x) < Metric.FINGER_SIZE) &&
				(Math.abs(v.y - y) < Metric.FINGER_SIZE);
	}
	
	public static class GameEngineHandler extends Handler {
		private final WeakReference<GameScreen> weakGame;
		
		public GameEngineHandler(GameScreen game) {
			weakGame = new WeakReference<GameScreen>(game);
		}
		
		@Override
		public void handleMessage(Message msg) {
			GameScreen game = weakGame.get();
			if (game == null) {
				return;
			}
		    switch(msg.what) {
		    	case MSG_NEXT_LEVEL:
		    		game.createLevel(++game.mLevel);
		    		break;
		    	case MSG_RESTART_LEVEL:
		    		game.createLevel(game.mLevel);
		    		break;
		    	case MSG_START_FAILANIM:
		    		game.mAnimator = new ScrollAwayAnimator();
					game.mLayout = new SingularityLayout();
		    		break;
		    	default:
		    		throw new RuntimeException("Unhandled msg.what: " + msg.what);
		    }
		}
    }

	@Override
    public boolean onBackPressed() {
	    mScreenManager.quitGame();
	    return true;
    }
}
