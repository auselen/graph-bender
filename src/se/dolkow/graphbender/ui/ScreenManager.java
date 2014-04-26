package se.dolkow.graphbender.ui;

public class ScreenManager {

	private ScreenTitle mScreenTitle;
	private Screen mActiveScreen;
	private ScreenGame mScreenGame;
	
	public ScreenManager() {
		mScreenTitle = new ScreenTitle();
		mScreenTitle.registerManager(this);
		mScreenGame = new ScreenGame();
		mScreenGame.registerManager(this);
		mActiveScreen = mScreenTitle;
	}

	public void startGame() {
		mActiveScreen = mScreenGame;
	}
	
	public Screen getScreen() {
		return mActiveScreen;
	}

	public void surfaceChanged(int width, int height) {
		mActiveScreen.surfaceChanged(width, height);
	}
}
