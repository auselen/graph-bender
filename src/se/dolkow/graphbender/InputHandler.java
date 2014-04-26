package se.dolkow.graphbender;

import android.view.MotionEvent;

public class InputHandler {

	private GameEngine mGameEngine;

	public InputHandler(GameEngine gameEngine) {
		mGameEngine = gameEngine;
	};

	public void onTouchEvent(MotionEvent event) {
		mGameEngine.onTouchEvent(event);
	}
}
