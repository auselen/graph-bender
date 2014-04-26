package se.dolkow.graphbender.ui;

import se.dolkow.graphbender.GameSurface;

public class ScreenManager {

	private ScreenTitle mScreenTitle;
	private Screen mActiveScreen;
	private ScreenGame mScreenGame;
	
	public ScreenManager(GameSurface gameSurface) {
		mScreenTitle = new ScreenTitle(gameSurface);
		mScreenTitle.registerManager(this);
		mScreenGame = new ScreenGame(gameSurface);
		mScreenGame.registerManager(this);
		mActiveScreen = mScreenTitle;
	}

	public void startGame() {
		mActiveScreen = mScreenGame;
	}
	
	public Screen getScreen() {
		return mActiveScreen;
	}
}
