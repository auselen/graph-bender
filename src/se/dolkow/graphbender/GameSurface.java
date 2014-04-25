package se.dolkow.graphbender;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView {
	private InputHandler inputHandler = InputHandler.getInstance();
	
	public GameSurface(Context context) {
		super(context);
	}	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		inputHandler.onTouchEvent(event);
		return true;
	}
}
