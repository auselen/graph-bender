package se.dolkow.graphbender;

import se.dolkow.graphbender.ui.ScreenManager;
import android.view.MotionEvent;

/*
 * Keeps the game flow
 */
public class GameEngine {
	private ScreenManager mScreenManager;

	public GameEngine(GameSurface surface) {
		mScreenManager = new ScreenManager(surface);
		new InputHandler(this);
	};

	public void onTouchEvent(MotionEvent event) {
		mScreenManager.getScreen().onTouchEvent(event);
	}
}
