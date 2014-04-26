package se.dolkow.graphbender.ui;

public class ScreenManager {

	private ScreenTitle mScreenTitle;
	private Screen mActiveScreen;
	private ScreenGame mScreenGame;
	
	public ScreenManager() {
		mScreenTitle = new ScreenTitle(this);
		mScreenGame = new ScreenGame(this);
		mActiveScreen = mScreenTitle;
	}

	public void startGame() {
		mActiveScreen = mScreenGame;
	}
	
	public Screen getScreen() {
		return mActiveScreen;
	}
}
